import { createRouter, createWebHistory } from "vue-router";
import TmapView from "@/views/TmapView.vue";


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "tmap",
      component: TmapView,
    },
  ],
});


export default router;
