package edu.lieineyes.sockets.models;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String login;
    private String password;

}

