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

      <div class="col-12">
        <div v-if="editing"> 파일 목록
          <ul>
            <li v-for="info in fileInfoList" :key="info.id">
              {{info.name}}
              <a @click="downloadFile(info.id,info.name)">[download]</a>
              <a @click="removeFile(info.id,info.name)">[remove]</a>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <DragAndDropTodoForm
        @updateFiles="modifiedFiles"
    />

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
import DragAndDropTodoForm from "@/components/DragAndDropTodoForm.vue";

export default {
  components: {
    Input,
    DragAndDropTodoForm
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
      body: '',
      files: []
    });
    let fileInfoList = ref([]);
    let realFileArr = ref([]);

    const subjectError = ref('');
    const originalTodo = ref(null);
    const loading = ref(false);
    const {
      toastMessage,
      toastAlertType,
      showToast,
      triggerToast
    } = useToast();
    const todoId = route.params.id;
    // const fileInput = ref(null);
    // const handleFileChange = () => {
    //   //todo.value.files= fileInput.value;
    //   // 여러 파일 선택 시, files 배열에 추가
    //   todo.value.files = Array.from(fileInput.value.files);
    //
    // }

    const modifiedFiles = (inputFile)=>{
      console.log("file size:"+inputFile.value.length);
      //realFileArr.value=[];
      realFileArr.value= inputFile.value;
      //console.log(inputFile.value instanceof  Array);
    };

    const FileDownload = require('js-file-download');
    const downloadFile = async (fileId,filename) =>{
      await TodoService.downLoadFile(fileId).then(
          (res)=>{
            console.log(res);
            FileDownload(res.data, filename);
          },
          (err)=>{
            console.log(err.response.data.code);
            console.log(err.response.data.message);
            console.log(err.response.data.status);
            triggerToast(err.response.data.message, 'danger');
          }
      )
    };

    const removeFile = async (fileId,filename) =>{
      console.log(filename);
      await TodoService.removeFile(fileId).then(
          (res)=>{
            console.log(res);

            //const removeFile = fileInfoList.value.filter(file => _.isEqual(fileId,file.id));
            let number = fileInfoList.value.findIndex(file=>_.isEqual(fileId,file.id));

            console.log("fileInfoList size :" + fileInfoList.value.length);
            console.log("index:"+number);
            fileInfoList.value.splice(number,1);
            //fileInfoList = [];
            console.log("fileInfoList size :" + fileInfoList.value.length);

            triggerToast("파일이 삭제 되었습니다.", 'success');
          },
          (err)=>{
            console.log(err.response.data.code);
            console.log(err.response.data.message);
            console.log(err.response.data.status);
            triggerToast(err.response.data.message, 'danger');
          }
      )
    };

    const getTodo = () => {
      loading.value = true;
      TodoService.getTodo(todoId).then(
          (res) => {
            todo.value = {...res.data};
            originalTodo.value = {...res.data};
            loading.value = false;
            fileInfoList.value = [...res.data.files];
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
      //console.log("todo.value:"+JSON.stringify(todo.value));
      //console.log("originalTodo.value:"+JSON.stringify(originalTodo.value));
      let updateBoolean = false;
      if(!_.isEqual(todo.value, originalTodo.value)){
          updateBoolean = true;
      }if(!_.isEqual(realFileArr.value.length , 0)){
          updateBoolean = true;
      }
      return updateBoolean;
      //return !_.isEqual(todo.value, originalTodo.value);
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

      const formData = new FormData();
      formData.append('todoReq'
          ,new Blob([JSON.stringify({ subject: todo.value.subject, body: todo.value.body,completed: _.upperCase(todo.value.completed) })], {
            type: 'application/json'
          }));

      // 여러 파일 추가
      realFileArr.value.forEach((file, index) => {
        console.log(index);
        formData.append('files', file);
      });
      /**
      todo.value.files.forEach((file, index) => {
        console.log(index);
        formData.append('files', file);
      });
      **/

      if (props.editing) {
        await TodoService.updateTodo(todoId, formData).then(
            (res) => {
              originalTodo.value = {...res.data};
              const message = 'Successfully ' + (props.editing ? 'Updated!' : 'Created!');
              triggerToast(message);
              router.push({
                name: 'Todos'
              });
            }, (err) => {
              console.log(err.response.data.code);
              console.log(err.response.data.message);
              console.log(err.response.data.status);
              triggerToast(err.response.data.message, 'danger');
            }
        );
      } else {
        await TodoService.createTodo(formData).then(
            () => {
              todo.value.subject = '';
              todo.value.body = '';
              // fileInput.value = '';
              todo.value.files = [];

              const message = 'Successfully ' + (props.editing ? 'Updated!' : 'Created!');
              triggerToast(message);
              router.push({
                name: 'Todos'
              });
            }, (err) => {
              console.log(err.response.data.code);
              console.log(err.response.data.message);
              console.log(err.response.data.status);
              triggerToast(err.response.data.message, 'danger');
            }
        );
      }
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
      downloadFile,
      modifiedFiles,
      fileInfoList,
      removeFile
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