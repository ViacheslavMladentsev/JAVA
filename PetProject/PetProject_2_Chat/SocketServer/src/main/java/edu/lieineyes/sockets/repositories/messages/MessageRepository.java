package edu.school21.sockets.repositories.messages;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.crud.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message> {
    List<Message> findByAuthorId(Long authorId);
    List<Message> findByRoomId(Long roomId);
    List<Message> findLastSomeMessagesByNameRoom(String nameRoom, Long countMessage);
}
