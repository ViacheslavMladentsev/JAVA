package edu.lieineyes.repositories;

import edu.lieineyes.models.User;
import edu.lieineyes.exception.EntityNotFoundException;

public interface UsersRepository {
    User findByLogin(String login) throws EntityNotFoundException;
    void update(User user) throws EntityNotFoundException;
}
