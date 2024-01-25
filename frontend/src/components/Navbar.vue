<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <router-link class="navbar-brand" :to="{ name: 'Home'}">
      Kossie Coder
    </router-link>

    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <router-link class="nav-link" :to="{ name: 'Todos'}">
          Todos
        </router-link>
      </li>
      <li class="nav-item active">
        <router-link class="nav-link" :to="{ name: 'Login'}">
          Login
        </router-link>
      </li>
      <li class="nav-item active">
        <router-link class="nav-link" :to="{ name: 'Sign'}">
          Sign-In
        </router-link>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {

}
</script>

<style>

</style>