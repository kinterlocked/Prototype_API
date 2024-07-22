<template>
  <div>
   
    <main>
      <RouterView />
    </main>
  </div>
</template>

<script setup>

</script>

<style scoped>
main {
  margin-left: 200px; /* nav바 너비만큼 왼쪽 마진을 줍니다 */
  padding: 20px;
  flex: 1;
}
</style>
