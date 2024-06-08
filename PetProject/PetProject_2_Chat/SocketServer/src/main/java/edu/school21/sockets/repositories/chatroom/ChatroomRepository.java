package edu.school21.sockets.repositories.chatroom;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.repositories.crud.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository extends CrudRepository<Chatroom> {
    Optional<Chatroom> findByName(String nameChatroom);
    List<Chatroom> findAllNameRoom();
}
