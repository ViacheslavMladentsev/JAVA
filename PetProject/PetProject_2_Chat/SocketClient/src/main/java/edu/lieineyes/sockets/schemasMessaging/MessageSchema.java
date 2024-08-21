package edu.lieineyes.sockets.schemasMessaging;

import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MessageSchema {

    private edu.lieineyes.sockets.schemasMessaging.UserSchema author;
    private String message;

}
