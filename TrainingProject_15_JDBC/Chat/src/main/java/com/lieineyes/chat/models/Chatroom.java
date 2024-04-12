package com.lieineyes.chat.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Chatroom {

    private Long id;
    private String name;
    private User owner_id;
    private List<Message> messagesInChatroom;

}
