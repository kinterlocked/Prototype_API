<template>

    <div class="kakao1">
        <img class="kakao2" src="@/assets/image/kakao.png" @click="kakaoLogin" />
    </div>

</template>

<script setup>
import { ref } from 'vue'

const user = ref({
    email: "",
    password: ""
})

//1. 카카오로그인 버튼 클릭
const kakaoLogin = function () {
    const redirect_uri = "프론트 리다이렉트 경로"
    const clientId = "REST API 키"
    const Auth_url = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${clientId}&redirect_uri=${redirect_uri}`
    //2. 카카오 서버에 로그인 요청 보냄
    window.location.href = Auth_url

    router.push({ name: 'home' })
}

</script>

<style scoped>

.kakao1 {
    width: 270px;
    display: flex;
    justify-content: center;
}

.kakao2 {
    margin-top: 20px;
    width: 100%;
    display: flex;
    justify-content: center;
    height: 40px;
    cursor: pointer;
}

</style>