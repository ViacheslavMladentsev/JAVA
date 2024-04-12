package com.lieineyes.chat.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Message {

    private Long id;
    private User author;
    private Chatroom chatroom;
    private String text;
    private LocalDateTime date;

    @Override
    public String toString() {
        return "Message : {\n" +
                "id=" + id + ",\n" +
                "author=" + author + ",\n" +
                "chatroom=" + chatroom + ",\n" +
                "text='" + text + '\'' + ",\n" +
                "date=" + date + "\n" +
                "\t}";
    }
}
