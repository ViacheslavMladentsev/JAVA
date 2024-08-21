package edu.lieineyes.sockets.repositories.users;

import edu.lieineyes.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UsersRepository {

    private static final String SEARCH_QUERY_BY_LOGIN = "SELECT * FROM users WHERE login=?;";

    private static final String SEARCH_QUERY_BY_ID = "SELECT * FROM users WHERE id=?";

    private static final String SEARCH_QUERY_BY_ALL = "SELECT * FROM users";

    private static final String SAVE_USER = "INSERT INTO users (login, password) VALUES (?, ?);";

    private static final String UPDATE_USER = "UPDATE users SET login = ?, password = ? WHERE id = ?;";

    private static final String DELETE_USER = "DELETE FROM users WHERE id=?;";

    private static final String FIND_LAST_ROW = "SELECT * FROM users ORDER BY id DESC LIMIT 1";

    private static final String COLUMN_ID = "id";

    private static final String NAME_BEAN_FOR_DATASOURCE = "hikariDataSource";


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryImpl(@Qualifier(NAME_BEAN_FOR_DATASOURCE) DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return this.jdbcTemplate.query(SEARCH_QUERY_BY_LOGIN,
                        new Object[]{login},
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
    public void save(User entity) throws RuntimeException {
        try {
            this.jdbcTemplate.update(SAVE_USER, entity.getLogin(), entity.getPassword());

            User user = this.jdbcTemplate.query(FIND_LAST_ROW, new BeanPropertyRowMapper<>(User.class))
                    .stream()
                    .findAny()
                    .get();
            Field field = entity.getClass().getDeclaredField(COLUMN_ID);
            field.setAccessible(true);
            field.set(entity, user.getId());
            field.setAccessible(false);
        } catch (DataAccessException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User entity) {
        this.jdbcTemplate.update(UPDATE_USER,
                entity.getLogin(), entity.getPassword(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        this.jdbcTemplate.update(DELETE_USER, id);
    }

}
