package edu.lieineyes.sockets.services.chatroom;

import edu.lieineyes.sockets.models.Chatroom;
import edu.lieineyes.sockets.models.User;
import edu.lieineyes.sockets.repositories.chatroom.ChatroomRepository;
import edu.lieineyes.sockets.repositories.chatroom.ChatroomRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomServiceImpl implements ChatRoomService<Chatroom> {

    private static final String NAME_BEAN_FOR_CHATROOM_REPOSITORY = "chatroomRepositoryImpl";

    private final ChatroomRepository chatroomRepository;


    @Autowired
    public ChatRoomServiceImpl(@Qualifier(NAME_BEAN_FOR_CHATROOM_REPOSITORY) ChatroomRepositoryImpl chatroomRepository) {
        this.chatroomRepository = chatroomRepository;
    }

    @Override
    public void createRoom(String nameRoom, User owner_id) {
        try {
            this.chatroomRepository.save(new Chatroom(null, nameRoom, owner_id));
        } catch (RuntimeException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
