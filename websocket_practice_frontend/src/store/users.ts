import { defineStore } from 'pinia';
import { computed, reactive } from 'vue';

export const useUsersStore = defineStore('users', () => {
  const state = reactive({ id: -1, nickname: '', profileImage: '' });
  const login = (nickname: string, profileImage: string) => {
    state.id = Math.floor(Math.random() * 1000000000000);
    state.nickname = nickname;
    state.profileImage = profileImage;
  };
  const logout = () => {
    state.id = -1;
    state.nickname = '';
    state.profileImage = '';
  };
  const isNotLogin = computed(() => state.id == -1);
  return { state, login, logout, isNotLogin };
});
