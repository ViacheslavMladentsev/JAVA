package edu.lieineyes.models;

import lombok.*;
import sun.security.util.Password;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {
    private Long id;
    private String login;
    private String password;
    private boolean isAuthentication;
}
