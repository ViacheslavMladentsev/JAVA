package edu.lieineyes.sockets.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private Long id;
    private User author;
    private Chatroom chatroom;
    private String message;
    private LocalDateTime date;

}
