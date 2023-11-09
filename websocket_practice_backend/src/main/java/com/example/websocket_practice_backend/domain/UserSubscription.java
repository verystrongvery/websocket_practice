package com.example.websocket_practice_backend.domain;


import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserSubscription {
    private final Map<Long, Map<Long, Users>> subscribedUsers = new HashMap<>();

    public void addSubscription(Long chatRoomId, Users user) {
        subscribedUsers.putIfAbsent(chatRoomId, new HashMap<>());
        subscribedUsers.get(chatRoomId).put(user.getId(), user);
    }

    public void removeSubscription(Long chatRoomId, Long userId) {
        subscribedUsers.get(chatRoomId).remove(userId);
    }

    public List<Users> findSubscribedUsers(Long chatRoomId) {
        return new ArrayList<>(subscribedUsers.get(chatRoomId).values());
    }
}
