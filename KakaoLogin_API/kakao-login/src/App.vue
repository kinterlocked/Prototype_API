<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <RouterLink :to="{name : 'login'}"> 로그인 </RouterLink>
  <RouterView />
</template>

<style scoped>

</style>
