import {createRouter, createWebHistory} from "vue-router";
import Home from '@/pages/index.vue';
import Todos from '@/pages/todos/index.vue';
import Todo from '@/pages/todos/_id.vue';

//  / : 홈
//  /todos : 조회
//  /todos/create : 등록
//  /todos/:id : 수정
const router = createRouter({
    history : createWebHistory(),
    routes : [
        {
            path : "/",
            name : "Home",
            component : Home
        },
        {
            path : "/todos",
            name : "Todos",
            component : Todos
        },
        {
            path : "/todos/:id",
            name : "Todo",
            component : Todo
        }
    ]

});

export default router;