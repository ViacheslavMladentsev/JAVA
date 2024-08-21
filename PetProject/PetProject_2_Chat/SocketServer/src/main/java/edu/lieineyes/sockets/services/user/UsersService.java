package edu.lieineyes.sockets.services.user;

import edu.lieineyes.sockets.models.User;

import java.util.Optional;

public interface UsersService<T> {
    void signUp(String login, String password);
    Optional<User> authorization(String login, String password);

}
