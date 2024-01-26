<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <router-link class="navbar-brand" :to="{ name: 'Home'}">
      나의 놀이터 대문
    </router-link>

    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <router-link class="nav-link" :to="{ name: 'Todos'}">
          할일들
        </router-link>
      </li>
      <li class="nav-item active">
        <router-link class="nav-link" :to="{ name: 'Login'}">
          들어가기
        </router-link>
      </li>
      <li class="nav-item active">
        <router-link class="nav-link" :to="{ name: 'Sign'}">
          참여하기
        </router-link>
      </li>
      <li class="nav-item active">
        <a class="nav-link" @click="signOut"> 종료하기 </a>
      </li>
    </ul>
  </nav>
</template>

<script>
import AuthService from "@/service/auth/auth.service";

export default {
  setup(){
    const signOut = ()=>{
        if(localStorage.getItem('accessToken')){
          AuthService.signOut().then(
              (res) => {
                console.log(res);
              },
              (err) => {
                console.log(err);
                // console.log(err.response.data.code);
                // console.log(err.response.data.message);
                // console.log(err.response.data.status);
                // triggerToast(err.response.data.message, 'danger');
              }
          );
        }
    }
    return{
      signOut,
    }
  }
}
</script>

<style>

</style>