package lieineyes.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import lieineyes.spring.service.models.User;
import lieineyes.spring.service.repositories.UsersRepository;

@Component
public class UsersServiceImpl implements UsersService {

    private static final String FIND_LAST_ROW = "SELECT * FROM users ORDER BY id DESC LIMIT 1";

    private final UsersRepository<User> usersRepository;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbcTemplateImpl") UsersRepository<User> usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) throws RuntimeException {
        try {
            this.usersRepository.save(new User(null, email, null));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return this.usersRepository.findByEmail(email).get().getPassword();
    }

}
