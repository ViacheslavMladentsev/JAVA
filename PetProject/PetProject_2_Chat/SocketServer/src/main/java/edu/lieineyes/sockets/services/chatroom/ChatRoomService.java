package edu.lieineyes.sockets.services.chatroom;

import edu.lieineyes.sockets.models.User;


public interface ChatRoomService<T> {
    void createRoom(String nameRoom, User owner_id);
}
