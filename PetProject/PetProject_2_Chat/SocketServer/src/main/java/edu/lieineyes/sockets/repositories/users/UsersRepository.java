package edu.lieineyes.sockets.repositories.users;

import edu.lieineyes.sockets.models.User;
import edu.lieineyes.sockets.repositories.crud.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findByLogin(String login);
}
