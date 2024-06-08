package edu.school21.sockets.services.user;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsersServiceImpl implements UsersService<User> {

    private static final String NAME_BEAN_FOR_USERS_REPOSITORY = "usersRepositoryImpl";

    private static final String NAME_BEAN_FOR_PASSWORD_ENCODER = "passwordEncoder";

    private final UsersRepository usersRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UsersServiceImpl(@Qualifier(NAME_BEAN_FOR_USERS_REPOSITORY) UsersRepository usersRepository,
                            @Qualifier(NAME_BEAN_FOR_PASSWORD_ENCODER) PasswordEncoder encoder) {
        this.usersRepository = usersRepository;
        this.encoder = encoder;
    }

    @Override
    public void signUp(String login, String password) throws RuntimeException {
        try {
            this.usersRepository.save(new User(null, login, encoder.encode(password)));
        } catch (RuntimeException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> authorization(String login, String password) {
        if (this.usersRepository.findByLogin(login).isPresent()) {
            Optional<User> currentUser = this.usersRepository.findByLogin(login);
            if (encoder.matches(password, currentUser.get().getPassword())) {
                return currentUser;
            }
        }
        return Optional.empty();
    }

}
