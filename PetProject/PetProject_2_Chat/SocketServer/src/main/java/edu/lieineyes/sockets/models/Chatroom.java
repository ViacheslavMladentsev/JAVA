package edu.lieineyes.sockets.models;

import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Chatroom {

    private Long id;
    private String name;
    private User owner;

}
