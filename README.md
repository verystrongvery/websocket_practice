# 📜 **프로젝트 요약**

- 실시간 채팅 기능 구현

# 📅 프로젝트 기간

- 2023.09 ~ 2023.10

# 🫙 Github

- https://github.com/verystrongvery/websocket_practice

# 🛠 기술 스택

- Spring Boot 2, STOMP, Vue 3

# 🏫 프로젝트에서 배운점

- WebSocket 기반으로 동작하는 STOMP를 활용하여 실시간 채팅 기능 구현
- Vuetify, Pinia 등 Vue 라이브러리 활용하여, 실시간 채팅 화면단 구현

# 🖥 **프로젝트 구현 내용**

### 1. 웹소켓 관련 설정 클래스 생성

- 메시지 브로커 활성화
- 웹소켓 연결 및 해제를 위한 prefix 등록
- 구독, 구독 취소 등을 위한 토픽 prefix 등록
- 메시지 전송을 위한 메시지 컨트롤러 prefix 등록

### 2. 웹소켓을 통해 메시지를 주고받기 위한 메시지 컨트롤러 생성

- 채팅방 입장시, 구독자들에게 입장 메시지를 보냄
- 채팅방 퇴장시, 구독자들에게 퇴장 메시지를 보냄
- 메시지 전송시, 구독자들에게 입력한 메시지를 보냄

# 🍊 **실시간 채팅 과정**

### 1. 채팅방 입장하기 버튼 클릭

![](https://velog.velcdn.com/images/topmedia/post/da6f4d32-8fde-4199-abe3-372b7c2f84bc/image.png)

1. Vue Pinia를 활용하여 유저 정보 상태 관리
2. 채팅방 주소로 이동

### 2. 채팅방 입장

![](https://velog.velcdn.com/images/topmedia/post/fc240285-1d7d-4005-ba3a-49219a2ae8dd/image.png)

1. 웹소켓 통신 연결
2. 채팅방(토픽) 구독
3. 사람들(구독자들)에게 입장 메시지를 보내는 메시지 컨트롤러의 API 호출
4. 사람들(구독자들)에게 메시지가 전송되어, 채팅방에 입장 메시지가 출력됨

### 3. 메시지 전송

![](https://velog.velcdn.com/images/topmedia/post/cc5b6aea-a9e5-4ece-ab12-0d5df5d01cf4/image.png)

1. 메세지를 적고 Enter 클릭
2. 사람들(구독자들)에게 채팅 메시지를 보내는 메시지 컨트롤러의 API 호출
3. 사람들(구독자들)에게 메시지가 전송되어, 채팅방에 채팅 메시지가 출력됨

### 4. 채팅방 퇴장

![](https://velog.velcdn.com/images/topmedia/post/d16be41f-a22e-4c2d-9485-625ec745a292/image.png)

1. 사람들(구독자들)에게 퇴장 메시지를 보내는 메시지 컨트롤러의 API 호출
2. 사람들(구독자들)에게 퇴장 메시지가 전송되어, 채팅방에 퇴장 메시지가 출력됨
3. 채팅방(토픽) 구독 취소
4. 웹소켓 통신 연결 종료
5. Vue Pinia에서 상태 관리 하던 유저 정보 삭제
