package edu.school21.sockets.services.message;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.messages.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageServiceImpl implements MessageService<Message> {

    private static final String NAME_BEAN_FOR_MESSAGE_REPOSITORY = "messageRepositoryImpl";

    private static final Long LAST_THIRTY_MESSAGE = 30L;

    private final MessageRepository messageRepository;


    @Autowired
    public MessageServiceImpl(@Qualifier(NAME_BEAN_FOR_MESSAGE_REPOSITORY) MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getLastThirtyMessage(String nameRoom) {
        return this.messageRepository.findLastSomeMessagesByNameRoom(nameRoom, LAST_THIRTY_MESSAGE);
    }

}
