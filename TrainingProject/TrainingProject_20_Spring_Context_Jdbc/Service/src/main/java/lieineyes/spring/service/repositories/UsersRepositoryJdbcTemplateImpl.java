package lieineyes.spring.service.repositories;

import lieineyes.spring.service.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    private static final String SEARCH_QUERY_BY_EMAIL = "SELECT * FROM users WHERE email=?;";

    private static final String SEARCH_QUERY_BY_ID = "SELECT * FROM users WHERE id=?";

    private static final String SEARCH_QUERY_BY_ALL = "SELECT * FROM users";

    private static final String SAVE_USER = "INSERT INTO users (email, password) VALUES (?, ?);";

    private static final String UPDATE_USER = "UPDATE users SET email = ?, password = ? WHERE id = ?;";

    private static final String DELETE_USER = "DELETE FROM users WHERE id=?;";

    private static final String FIND_LAST_ROW = "SELECT * FROM users ORDER BY id DESC LIMIT 1";

    private static final String COLUMN_ID = "id";


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.jdbcTemplate.query(SEARCH_QUERY_BY_EMAIL,
                        new Object[]{email},
                        new BeanPropertyRowMapper<>(User.class))
                .stream()
                .findAny();
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.jdbcTemplate.query(SEARCH_QUERY_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(User.class))
                .stream()
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return this.jdbcTemplate.query(SEARCH_QUERY_BY_ALL, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(Object entity) throws RuntimeException {
        ((User) entity).setPassword(generatePassword());
        this.jdbcTemplate.update(SAVE_USER, ((User) entity).getEmail(), ((User) entity).getPassword());

        User user = this.jdbcTemplate.query(FIND_LAST_ROW, new BeanPropertyRowMapper<>(User.class))
                .stream()
                .findAny()
                .get();
        try {
            Field field = entity.getClass().getDeclaredField(COLUMN_ID);
            field.setAccessible(true);
            field.set(entity, user.getId());
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalArgumentException | SecurityException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String generatePassword() {
        char[] pass = new char[10];
        for (int i = 0; i < pass.length; i++) {
            pass[i] = (char) ((Math.random() * 92) + 33);
        }
        return new String(pass);
    }

    @Override
    public void update(Object entity) {
        User user = (User) entity;
        this.jdbcTemplate.update(UPDATE_USER, user.getEmail(), user.getPassword(), user.getId());
    }

    @Override
    public void delete(Long id) {
        this.jdbcTemplate.update(DELETE_USER, id);
    }

}
