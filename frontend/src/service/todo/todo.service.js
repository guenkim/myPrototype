import AxiosInst from "@/service/api";

class TodoService {
    getTodos(searchText,page,limit){
        return  AxiosInst.get(`todos?subject=${searchText}&page=${page}&size=${limit}`);
    }

    deleteTodo(todoId){
        return AxiosInst.delete('todos/' + todoId);
    }

    toggleTodo(todoId, completed){
        return AxiosInst.patch(`todos/${todoId}/` + completed);
    }

    getTodo(todoId){
        return AxiosInst.get(`todos/${todoId}`);
    }

    createTodo(data){
        return  AxiosInst.post('todos', data);
    }

    updateTodo(todoId,data){
        return AxiosInst.put(`todos/${todoId}`, data);
    }

}

export default new TodoService();
