<template>
  <v-container>
    <v-row style="background-color: grey">
      <v-col>
        <v-icon size="35"> mdi-account</v-icon>
        <span class="font-weight-bold text-h5">{{ chatRoomCount }}</span>
        <span class="font-weight-bold text-h5 ml-5">채팅방</span>
      </v-col>
      <RouterLink to="/">
        <v-col cols="1">
          <v-btn class="font-weight-bold text-h6">나가기</v-btn>
        </v-col>
      </RouterLink>
    </v-row>
    <v-row>
      <v-col cols="9" class="scroll-container">
        <div v-for="(chatContent, index) in chatContents" v-bind:key="index">
          <div v-if="chatContent.sendMessageType === 'ENTER'">
            <v-row>
              <v-col />
              <v-col cols="3">
                <v-card>
                  <v-card-text :style="{ textAlign: 'center', backgroundColor: 'pink' }" class="font-weight-bold"> {{ chatContent.user.nickname }}님이 들어왔습니다.</v-card-text>
                </v-card>
              </v-col>
              <v-col />
            </v-row>
          </div>
          <div v-if="chatContent.sendMessageType === 'EXIT'">
            <v-row>
              <v-col />
              <v-col cols="3">
                <v-card>
                  <v-card-text :style="{ textAlign: 'center', backgroundColor: 'grey' }" class="font-weight-bold"> {{ chatContent.user.nickname }}님이 나갔습니다.</v-card-text>
                </v-card>
              </v-col>
              <v-col />
            </v-row>
          </div>
          <div v-else-if="chatContent.sendMessageType === 'TALK'">
            <v-row v-if="chatContent.user.id !== user.state.id && (index === 0 || chatContent.user.id !== chatContents[index - 1].user.id)" style="height: 30px">
              <v-col :style="{ display: 'grid', gridTemplateColumns: '50px auto' }">
                <v-img :src="chatContent.profileImage" width="30px" style="border-radius: 100%" />
                <span class="font-weight-bold">{{ chatContent.user.nickname }}</span>
              </v-col>
            </v-row>
            <v-row v-if="chatContent.user.id === user.state.id">
              <v-col cols="3" />
              <v-col :style="{ fontSize: '10px', display: 'grid', justifyContent: 'end', gridTemplateColumns: 'auto auto' }" cols="9">
                <span v-if="index === chatContents.length - 1 || chatContent.user.id !== chatContents[index + 1].user.id || formatTimeChat(chatContent.time) !== formatTimeChat(chatContents[index + 1].time)" :style="{ fontSize: '10px', alignSelf: 'end', width: '35px' }" class="font-weight-bold">{{ formatTimeChat(chatContent.time) }}</span>
                <span v-else :style="{ width: '35px' }" />
                <v-card color="yellow">
                  <v-card-text :style="{ whiteSpace: 'pre-wrap' }">{{ chatContent.content }}</v-card-text>
                </v-card>
              </v-col>
            </v-row>
            <v-row v-if="chatContent.user.id !== user.state.id">
              <v-col :style="{ display: 'grid', gridTemplateColumns: '40px auto 35px', justifyContent: 'start', justifyItems: 'end' }" cols="9">
                <span />
                <v-card>
                  <v-card-text :style="{ whiteSpace: 'pre-wrap' }">{{ chatContent.content }}</v-card-text>
                </v-card>
                <span v-if="index === chatContents.length - 1 || chatContent.user.id !== chatContents[index + 1].user.id || formatTimeChat(chatContent.time) !== formatTimeChat(chatContents[index + 1].time)" :style="{ fontSize: '10px', display: 'grid', alignItems: 'end' }" class="font-weight-bold">{{ formatTimeChat(chatContent.time) }} </span>
              </v-col>
            </v-row>
          </div>
        </div>
      </v-col>
      <v-col cols="3">
        <v-card v-for="(chatUser, index) in chatUsers" v-bind:key="index" :style="{ display: 'grid', gridTemplateColumns: '2fr 1fr 4fr', justifyItems: 'center', alignItems: 'center' }" class="mb-3">
          <v-img :src="chatUser.profileImage" height="60px" width="60px" :style="{ borderRadius: 100 + '%' }" />
          <v-card-text v-if="chatUser.id === user.state.id" :style="{ backgroundColor: 'pink' }" class="font-weight-bold">나</v-card-text>
          <v-card-text v-else />
          <v-card-text class="font-weight-bold">{{ chatUser.nickname }}</v-card-text>
        </v-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-textarea append-inner-icon="mdi-comment" rows="1" v-model="chatContent" @click:append-inner="handleChatInput" @keypress.enter="handleChatInput" :style="{ whiteSpace: 'pre' }"></v-textarea>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { reactive, ref, nextTick, watch, onBeforeUnmount, onMounted } from 'vue';
import { useUsersStore } from '@/store/users';
import { Client } from '@stomp/stompjs';
import { formatTimeChat } from '@/utils/dateUtils';
import router from '@/router';

const user = useUsersStore();

const chatContents = reactive([]);
const chatRoomCount = ref(0);
const stompClient = ref(null);
const chatUsers = ref();
const chatContent = ref('');

const exitChatRoom = () => {
  if (stompClient.value == null) {
    return;
  }
  stompClient.value.publish({
    destination: '/app/exit-chat-room/' + '1',
    body: JSON.stringify(user.state)
  });
  stompClient.value.unsubscribe('/topic/' + '1');
  stompClient.value.deactivate();
  user.logout();
};

const handleSubscribeMessage = (message) => {
  const messageBody = JSON.parse(message.body);
  chatContents.push(messageBody);
  chatUsers.value = messageBody.subscribedUsers;
  chatRoomCount.value = chatUsers.value.length;
};

const handleChatInput = (event) => {
  if (event.shiftKey) {
    return;
  }
  event.preventDefault();
  stompClient.value.publish({
    destination: '/app/send-message/' + '1',
    body: JSON.stringify({ user: user.state, content: chatContent.value })
  });
  chatContent.value = '';
};
const handleStompClientOnConnect = () => {
  stompClient.value.subscribe('/topic/' + '1', handleSubscribeMessage);
  stompClient.value.publish({
    destination: '/app/enter-chat-room/' + '1',
    body: JSON.stringify(user.state)
  });
};

const initStompClient = () => {
  stompClient.value = new Client({
    brokerURL: 'ws://localhost:8080/ws',
    onConnect: handleStompClientOnConnect,
    onStompError: () => window.location.reload()
  });
  stompClient.value.activate();
};

const moveCurrentUserTop = () => {
  const currentUserIndex = chatUsers.value.findIndex((chatUser) => chatUser.id === user.state.id);
  const currentUser = chatUsers.value.splice(currentUserIndex, 1)[0];
  chatUsers.value.unshift(currentUser);
};

const fixScrollPositionBottom = () => {
  nextTick(() => (document.querySelector('.scroll-container').scrollTop = document.querySelector('.scroll-container').scrollHeight));
};

onMounted(() => {
  if (user.isNotLogin) {
    router.push('/');
    return;
  }
  window.addEventListener('beforeunload', exitChatRoom);
  initStompClient();
});

onBeforeUnmount(exitChatRoom);

watch(chatUsers, moveCurrentUserTop);

watch(chatContents, fixScrollPositionBottom);
</script>
<style scoped>
.scroll-container {
  height: 550px;
  overflow-y: auto;
}
</style>
