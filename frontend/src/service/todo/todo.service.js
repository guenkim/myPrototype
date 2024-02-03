import AxiosInst from "@/service/api";

class TodoService {
    getTodos(searchText,page,limit,sortArr){
        let url = '';
        url = `todos?subject=${searchText}&page=${page}&size=${limit}`;
        sortArr.value.forEach(sort => url += '&' +  sort.value);
        console.log("url:"+url);
        //return  AxiosInst.get(`todos?subject=${searchText}&page=${page}&size=${limit}`);
        return  AxiosInst.get(url);
    }

    createTodo(data){
        return  AxiosInst.post('todos', data, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }

    updateTodo(todoId,data){
        return AxiosInst.put(`todos/${todoId}`, data,{
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

}

export default new TodoService();
