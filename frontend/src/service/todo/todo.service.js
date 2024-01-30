import AxiosInst from "@/service/api";

class TodoService {
    getTodos(searchText,page,limit){
        return  AxiosInst.get(`todos?subject=${searchText}&page=${page}&size=${limit}`);
    }

    createTodo(data){
        return  AxiosInst.post('todos', data, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }

    downLoadFile(fileId){
        return  AxiosInst.get(`file/${fileId}`,{
            responseType:"blob"
        });
    }

    removeFile(fileId){
        return  AxiosInst.delete(`file/${fileId}`);
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

    updateTodo(todoId,data){
        return AxiosInst.put(`todos/${todoId}`, data,{
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }

}

export default new TodoService();
