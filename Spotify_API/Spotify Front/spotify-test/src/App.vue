<script setup>
import { RouterLink, RouterView } from 'vue-router'


</script>

<template>
  <div>
    <RouterLink :to="{name : 'home'}">처음으로</RouterLink> | 
    <RouterLink :to="{name : 'result'}">결과보기</RouterLink>
  
    <RouterView />
  </div>
</template>

<style scoped>

</style>
