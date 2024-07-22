<template>
  <div>
    <div class="g-recaptcha" data-sitekey="YOUR_SITE_KEY" data-callback="onCaptchaVerified"></div>
    <form @submit.prevent="onSubmit">
      <!-- Your form fields -->
      <button type="submit">Submit</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      siteKey: 'YOUR_SITE_KEY', // 여기에 자신의 사이트 키를 입력하세요.
      captchaVerified: false,
      captchaToken: ''
    };
  },
  mounted() {
    this.loadReCaptchaScript();
    
  },
  methods: {
    loadReCaptchaScript() {
      const script = document.createElement('script');
      script.src = 'https://www.google.com/recaptcha/api.js';
      script.async = true;
      script.defer = true;
      document.head.appendChild(script);
    },
    onCaptchaVerified(token) {
      this.captchaToken = token;
      this.captchaVerified = true;
    },
    onSubmit() {
      if (this.captchaVerified) {
        // reCAPTCHA 토큰을 서버로 전송하여 검증
        axios.post('http://localhost:8080/recaptcha-verify', {
          token: this.captchaToken
        }).then(response => {
          if (response.data.success) {
            // 폼 제출 처리
            alert('Form submitted successfully!');
          } else {
            alert('reCAPTCHA verification failed.');
          }
        }).catch(error => {
          console.error(error);
        });
      } else {
        alert('Please complete the reCAPTCHA.');
      }
    }
  }
};
</script>
