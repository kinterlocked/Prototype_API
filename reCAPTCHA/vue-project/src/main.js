import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import { VueReCaptcha } from 'vue-recaptcha-v3';

const app = createApp(App)
// app.use(VueReCaptcha, { siteKey: 'YOUR_SITE_KEY' });
app.use(createPinia())
app.use(router)

app.mount('#app')
