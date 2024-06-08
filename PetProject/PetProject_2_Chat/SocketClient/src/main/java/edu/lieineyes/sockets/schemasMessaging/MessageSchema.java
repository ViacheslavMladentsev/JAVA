package edu.school21.sockets.schemasMessaging;

import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MessageSchema {

    private UserSchema author;
    private String message;

}
