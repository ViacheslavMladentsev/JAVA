package edu.school21.services;

import edu.school21.exception.AlreadyAuthenticatedException;
import edu.school21.exception.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl implements UsersService {

    private static final String ALREADY_AUTHENTICATED = "Already Authenticated";

    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository user) {
        this.usersRepository = user;
    }

    @Override
    public boolean authenticate(String login, String password) throws EntityNotFoundException, AlreadyAuthenticatedException {
        try {
            User user = usersRepository.findByLogin(login);
            if (user.isAuthentication()) {
                throw new AlreadyAuthenticatedException(ALREADY_AUTHENTICATED);
            }
            if (user.getPassword().equals(password)) {
                user.setAuthentication(true);
                return true;
            } else {
                return false;
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e);
        }
    }

}
