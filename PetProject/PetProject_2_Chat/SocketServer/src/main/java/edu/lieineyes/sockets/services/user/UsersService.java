package edu.school21.sockets.services.user;

import edu.school21.sockets.models.User;

import java.util.Optional;

public interface UsersService<T> {
    void signUp(String login, String password);
    Optional<User> authorization(String login, String password);

}
