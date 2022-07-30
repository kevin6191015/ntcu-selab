<template>
  <div id="app">
    <nav class="navbar navbar-expand-lg bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="https://ecsb.ntcu.edu.tw/newweb/index.htm">NTCU-SELAB</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <router-link to="/" class="nav-link active" aria-current="page" >Home</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/login" class="nav-link">Login</router-link >
        </li>
        <li class="nav-item">
          <router-link to="/getStudent" class="nav-link" >GetStudent</router-link>
        </li>
      </ul>
    </div>
  </div>
</nav>
<router-view/>
    <!-- <router-link to="/">Home</router-link> |
    <router-link to="/login">Login</router-link> |
    <router-link to="/getStudent">getStudent</router-link>
    <router-view/> -->
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
