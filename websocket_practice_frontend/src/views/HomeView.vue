<template>
  <v-card class="mx-auto px-6 py-8 mt-16" max-width="600">
    <v-row>
      <v-container>
        <v-item-group selected-class="bg-primary" v-model="selectedProfileImageIndex">
          <v-row>
            <v-col v-for="(image, index) in profileImages" :key="index">
              <v-item v-slot="{ selectedClass }" :key="index">
                <v-card :class="selectedClass" @click="selectedProfileImageIndex = index">
                  <v-img :key="index" :src="`/src/assets/${image}`"> </v-img>
                </v-card>
              </v-item>
            </v-col>
          </v-row>
        </v-item-group>
      </v-container>
    </v-row>
    <v-row>
      <v-text-field label="닉네임을 입력하세요" v-model="nickname" />
    </v-row>
    <v-row>
      <v-btn block color="success" @click="onEnterChatRoom">입장하기</v-btn>
    </v-row>
  </v-card>
</template>

<script setup lang="ts">
import router from '@/router';
import { ref } from 'vue';
import { useUsersStore } from '@/store/users';

const profileImages = ['profile1.png', 'profile2.png', 'profile3.png', 'profile4.png', 'profile5.png'];
const selectedProfileImageIndex = ref(0);
const nickname = ref('');
const user = useUsersStore();
const onEnterChatRoom = () => {
  user.login(nickname.value, '/src/assets/' + profileImages[selectedProfileImageIndex.value]);
  router.push('/chat');
};
</script>
<style scoped></style>
