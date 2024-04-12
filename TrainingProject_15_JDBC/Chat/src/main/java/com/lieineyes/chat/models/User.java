package com.lieineyes.chat.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

    private Long id;
    private String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> chatroomsSocializes;

}
