package edu.school21.sockets.services.chatroom;

import edu.school21.sockets.models.User;


public interface ChatRoomService<T> {
    void createRoom(String nameRoom, User owner_id);
}
