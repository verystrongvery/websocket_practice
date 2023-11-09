// Composables
import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@/views/HomeView.vue';
import ChatView from '@/views/ChatView.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    components: { default: HomeView }
  },
  {
    path: '/chat',
    name: 'chat',
    components: { default: ChatView }
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router;
