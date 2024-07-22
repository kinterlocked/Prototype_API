<template>
    <div>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import { RouterLink, RouterView } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const router = useRouter()

//3. 카카오에서 요청 처리 후 인가코드와 함께 KakaoLoginView.vue로 리다이렉트
//4. 카카오서버에서 받은 인가코드를 백엔드 서버로 전송
axios.get("http://localhost:8080/kakao-api/kakao-login/" + route.query.code)
.then((res) => {
    sessionStorage.setItem("loginUser", res.data.userId)
    sessionStorage.setItem("role", res.data.role)
    router.push({name : "home"})
    window.alert("로그인 성공")
})
.catch((err) => {
    window.alert("로그인 실패")
    router.push({name : "login"})
})


</script>

<style scoped></style>