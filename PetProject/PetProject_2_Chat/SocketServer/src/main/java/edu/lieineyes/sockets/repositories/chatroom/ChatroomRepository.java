package edu.lieineyes.sockets.repositories.chatroom;

import edu.lieineyes.sockets.models.Chatroom;
import edu.lieineyes.sockets.repositories.crud.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository extends CrudRepository<Chatroom> {
    Optional<Chatroom> findByName(String nameChatroom);
    List<Chatroom> findAllNameRoom();
}
