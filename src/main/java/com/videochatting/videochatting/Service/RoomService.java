package com.videochatting.videochatting.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final Map<String, CopyOnWriteArraySet<String>> rooms =
            new ConcurrentHashMap<>();

    public void joinRoom(String roomId, String userId) {

        rooms.computeIfAbsent(
                roomId,
                k -> new CopyOnWriteArraySet<>()
        ).add(userId);
    }

    public void leaveRoom(String roomId, String userId) {

        Set<String> users = rooms.get(roomId);

        if (users != null) {

            users.remove(userId);

            if (users.isEmpty()) {
                rooms.remove(roomId);
            }
        }
    }

    public int getParticipantCount(String roomId) {

        Set<String> users = rooms.get(roomId);

        return users == null ? 0 : users.size();
    }

    public Set<String> getParticipants(String roomId) {

        return rooms.getOrDefault(
                roomId,
                new CopyOnWriteArraySet<>()
        );
    }
}