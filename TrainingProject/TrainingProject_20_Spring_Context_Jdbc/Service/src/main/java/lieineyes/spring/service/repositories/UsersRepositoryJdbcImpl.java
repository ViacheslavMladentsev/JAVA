package lieineyes.spring.service.repositories;

import lieineyes.spring.service.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryJdbcImpl implements UsersRepository {

    private static final String SEARCH_QUERY_BY_EMAIL = "SELECT * FROM users WHERE email=?;";

    private static final String SEARCH_QUERY_BY_ID = "SELECT * FROM users WHERE id=?";

    private static final String SEARCH_QUERY_BY_ALL = "SELECT * FROM users";

    private static final String SAVE_USER = "INSERT INTO users (email, password) VALUES (?, ?);";

    private static final String UPDATE_USER = "UPDATE users SET email = ?, password = ? WHERE id = ?;";

    private static final String DELETE_USER = "DELETE FROM users WHERE id=?;";

    private static final String COLUMN_ID = "id";

    private static final String COLUMN_EMAIL = "email";

    private static final String COLUMN_PASSWORD = "password";


    private final DataSource dataSource;


    @Autowired
    public UsersRepositoryJdbcImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Optional<User> findByEmail(String email) throws RuntimeException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_QUERY_BY_EMAIL)) {
            statement.setString(1, email);
            return getUserByParameter(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<User> findById(Long id) throws RuntimeException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_QUERY_BY_ID)) {
            statement.setLong(1, id);
            return getUserByParameter(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<User> getUserByParameter(PreparedStatement statement) {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return Optional.of(new User(resultSet.getLong(COLUMN_ID),
                                            resultSet.getString(COLUMN_EMAIL),
                                            resultSet.getString(COLUMN_PASSWORD)));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<User> findAll() throws RuntimeException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_QUERY_BY_ALL)) {
            List<User> listUsers = new ArrayList<>(Collections.emptyList());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(resultSet.getLong(COLUMN_ID),
                                        resultSet.getString(COLUMN_EMAIL),
                                        resultSet.getString(COLUMN_PASSWORD));
                    listUsers.add(user);
                }
                return listUsers;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void save(Object entity) throws RuntimeException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(String.format(SAVE_USER),
                                                                                Statement.RETURN_GENERATED_KEYS)) {
            Field field = entity.getClass().getDeclaredField(COLUMN_EMAIL);
            field.setAccessible(true);
            preparedStatement.setString(1, (String) field.get(entity));
            field.setAccessible(false);

            String password = generatePassword();
            field = entity.getClass().getDeclaredField(COLUMN_PASSWORD);
            field.setAccessible(true);
            preparedStatement.setString(2, password);
            field.set(entity, password);
            field.setAccessible(false);

            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    field = entity.getClass().getDeclaredField(COLUMN_ID);
                    field.setAccessible(true);
                    field.set(entity, resultSet.getLong(COLUMN_ID));
                    field.setAccessible(false);
                }
            } catch (SQLException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
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
    public void update(Object entity) throws RuntimeException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            Field field = entity.getClass().getDeclaredField(COLUMN_EMAIL);
            field.setAccessible(true);
            preparedStatement.setString(1, (String) field.get(entity));
            field.setAccessible(false);

            field = entity.getClass().getDeclaredField(COLUMN_PASSWORD);
            field.setAccessible(true);
            preparedStatement.setString(2, (String) field.get(entity));
            field.setAccessible(false);

            field = entity.getClass().getDeclaredField(COLUMN_ID);
            field.setAccessible(true);
            preparedStatement.setLong(3, (Long) field.get(entity));
            field.setAccessible(false);
            preparedStatement.execute();
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(Long id) throws RuntimeException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
