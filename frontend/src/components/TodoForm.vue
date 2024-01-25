<template>
  <div v-if="loading">
    Loading..
  </div>
  <form
      v-else
      @submit.prevent="onSave"
  >
    <div class="row">
      <div class="col-6">
        <Input
            label="Subject"
            v-model:subject="todo.subject"
            :error="subjectError"
        />
      </div>
      <div v-if="editing" class="col-6">
        <div class="form-group">
          <label>Status</label>
          <div>
            <button
                class="btn"
                type="button"
                :class="todo.completed ? 'btn-success' : 'btn-danger'"
                @click="toggleTodoStatus"
            >
              {{ todo.completed ? 'Completed' : 'Incomplete' }}
            </button>
          </div>
        </div>
      </div>
      <div class="col-12">
        <div class="form-group">
          <label>Body</label>
          <textarea v-model="todo.body" class="form-control" cols="30" rows="10"></textarea>
        </div>
      </div>
    </div>

    <button
        type="submit"
        class="btn btn-primary"
        :disabled="!todoUpdated"
    >
      {{ editing ? 'Update' : 'Create' }}
    </button>
    <button
        class="btn btn-outline-dark ml-2"
        @click="moveToTodoListPage"
    >
      Cancel
    </button>
  </form>
</template>

<script>
import {useRoute, useRouter} from 'vue-router';
import {ref, computed} from 'vue';
import _ from 'lodash';
import {useToast} from '@/composables/toast';
import Input from '@/components/Input.vue';
import TodoService from "@/service/todo/todo.service";

export default {
  components: {
    Input
  },
  props: {
    editing: {
      type: Boolean,
      default: false
    }
  },
  setup(props) {

    const route = useRoute();
    const router = useRouter();
    const todo = ref({
      subject: '',
      completed: false,
      body: ''
    });

    const subjectError = ref('');
    const originalTodo = ref(null);
    const loading = ref(false);
    const {
      toastMessage,
      toastAlertType,
      showToast,
      triggerToast
    } = useToast();

    const todoId = route.params.id

    const getTodo = () => {
      loading.value = true;
      TodoService.getTodo(todoId).then(
          (res) => {
            todo.value = {...res.data};
            originalTodo.value = {...res.data};
            loading.value = false;
          },
          (err) => {
            loading.value = false;
            console.log(err.response.data.code);
            console.log(err.response.data.message);
            console.log(err.response.data.status);
            triggerToast(err.response.data.message, 'danger');
          }
      );
    };

    const todoUpdated = computed(() => {
      return !_.isEqual(todo.value, originalTodo.value)
    });

    const toggleTodoStatus = () => {
      todo.value.completed = !todo.value.completed;
    };

    const moveToTodoListPage = () => {
      router.push({
        name: 'Todos'
      })
    };

    if (props.editing) {
      getTodo();
    }

    const onSave = async () => {
      subjectError.value = '';
      if (!todo.value.subject) {
        subjectError.value = 'Subject is required';
        return;
      }

      const data = {
        subject: todo.value.subject,
        completed: _.upperCase(todo.value.completed), //todo.value.completed
        body: todo.value.body,
      };
      if (props.editing) {
        await TodoService.updateTodo(todoId, data).then(
            (res) => {
              originalTodo.value = {...res.data};
            }, (err) => {
              console.log(err.response.data.code);
              console.log(err.response.data.message);
              console.log(err.response.data.status);
              triggerToast(err.response.data.message, 'danger');
            }
        );
      } else {
        await TodoService.createTodo(data).then(
            () => {
              todo.value.subject = '';
              todo.value.body = '';
            }, (err) => {
              console.log(err.response.data.code);
              console.log(err.response.data.message);
              console.log(err.response.data.status);
              triggerToast(err.response.data.message, 'danger');
            }
        );
      }

      const message = 'Successfully ' + (props.editing ? 'Updated!' : 'Created!');
      triggerToast(message);


      router.push({
        name: 'Todos'
      });

    };

    return {
      todo,
      loading,
      toggleTodoStatus,
      moveToTodoListPage,
      onSave,
      todoUpdated,
      showToast,
      toastMessage,
      toastAlertType,
      subjectError,
    };
  }
}
</script>

<style scoped>

.fade-enter-active,
.fade-leave-active {
  transition: all 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(-30px);
}

.fade-enter-to,
.fade-leave-from {
  opacity: 1;
  transform: translateY(0px);
}
</style>