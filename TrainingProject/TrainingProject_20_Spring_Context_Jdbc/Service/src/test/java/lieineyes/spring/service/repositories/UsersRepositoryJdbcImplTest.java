package lieineyes.spring.service.repositories;

import lieineyes.spring.service.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;


class UsersRepositoryJdbcImplTest {

    private static final String DROP_TABLE_USERS = "DROP TABLE IF EXISTS users;";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE users (id serial PRIMARY KEY," +
            "email varchar(50)," +
            "password varchar(50));";

    private static final List<String> ARRAY_EMAIL = Arrays.asList("ivan@yandex.ru",
            "petr@gmail.com",
            "nikolay@mail.ru",
            "sergey@ngs.ru",
            "alex@tesla.com");

    private static final User EXPECTED_USER_FOR_FIND_BY_EMAIL = new User(3L, "nikolay@mail.ru", null);
    private static final String EMAIL_CORRECT_FOR_FIND_BY_EMAIL = "nikolay@mail.ru";
    private static final String EMAIL_ABSENT_FOR_FIND_BY_EMAIL = "111@222.ru";

    private static final User EXPECTED_USER_FOR_FIND_BY_ID = new User(4L, "sergey@ngs.ru", null);
    private static final Long ID_CORRECT_FOR_FIND_BY_ID = 4L;
    private static final Long ID_ABSENT_FOR_FIND_BY_ID = 10L;

    private static final List<User> EXPECTED_ARRAY_USERS_FOR_FIND_ALL = Arrays.asList(new User(1L, "ivan@yandex.ru", null),
            new User(2L, "petr@gmail.com", null),
            new User(3L, "nikolay@mail.ru", null),
            new User(4L, "sergey@ngs.ru", null),
            new User(5L, "alex@tesla.com", null));

    private static final User USER_FOR_SAVE = new User(null, "qwerty@qwerty.com", null);
    private static final List<User> EXPECTED_ARRAY_USERS_AFTER_SAVE = Arrays.asList(new User(1L, "ivan@yandex.ru", null),
            new User(2L, "petr@gmail.com", null),
            new User(3L, "nikolay@mail.ru", null),
            new User(4L, "sergey@ngs.ru", null),
            new User(5L, "alex@tesla.com", null),
            new User(6L, "qwerty@qwerty.com", null));

    private static final User USER_FOR_UPDATE = new User(3L, "qqqq@wwww.ee", null);

    private static final Long ID_USER_FOR_DELETE = 2L;


    private DataSource dataSource;

    private UsersRepository<User> usersRepository;

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setName("HyperSQL")
                .setType(HSQL)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScripts("schema.sql", "data.sql")
                .build();
        usersRepository = new UsersRepositoryJdbcImpl(dataSource);
    }

    @AfterEach
    void shootDownDB() {
        if (dataSource != null) {
            ((EmbeddedDatabase) dataSource).shutdown();
        }
    }

    @Test
    void findByEmailSuccess() {
        if (usersRepository.findByEmail(EMAIL_CORRECT_FOR_FIND_BY_EMAIL).isPresent()) {
            User actualUser = usersRepository.findByEmail(EMAIL_CORRECT_FOR_FIND_BY_EMAIL).get();
            EXPECTED_USER_FOR_FIND_BY_EMAIL.setPassword(actualUser.getPassword());
            assertEquals(EXPECTED_USER_FOR_FIND_BY_EMAIL, (User) usersRepository.findByEmail(EMAIL_CORRECT_FOR_FIND_BY_EMAIL).get());
        }
    }

    @Test
    void findByEmailAbsent() {
        if (usersRepository.findByEmail(EMAIL_ABSENT_FOR_FIND_BY_EMAIL).isPresent()) {
            assertEquals(Optional.empty(), (User) usersRepository.findByEmail(EMAIL_ABSENT_FOR_FIND_BY_EMAIL).get());
        }
    }

    @Test
    void findByIdSuccess() {
        if (usersRepository.findById(ID_CORRECT_FOR_FIND_BY_ID).isPresent()) {
            User actualUser = usersRepository.findById(ID_CORRECT_FOR_FIND_BY_ID).get();
            EXPECTED_USER_FOR_FIND_BY_ID.setPassword(actualUser.getPassword());
            assertEquals(EXPECTED_USER_FOR_FIND_BY_ID, (User) usersRepository.findById(ID_CORRECT_FOR_FIND_BY_ID).get());
        }
    }

    @Test
    void findByIdAbsent() {
        if (usersRepository.findById(ID_ABSENT_FOR_FIND_BY_ID).isPresent()) {
            assertEquals(Optional.empty(), (User) usersRepository.findById(ID_ABSENT_FOR_FIND_BY_ID).get());
        }
    }

    @Test
    void findAll() {
        System.out.println("EXPECTED " + EXPECTED_ARRAY_USERS_FOR_FIND_ALL);
        System.out.println("ACTUAL   " + usersRepository.findAll());
        for (User user : EXPECTED_ARRAY_USERS_FOR_FIND_ALL) {
            assertEquals(user.getId(), usersRepository.findById(user.getId()).get().getId());
            assertEquals(user.getEmail(), usersRepository.findById(user.getId()).get().getEmail());
        }
    }

    @Test
    void save() {
        try {
            usersRepository.save(USER_FOR_SAVE);
            for (User user : EXPECTED_ARRAY_USERS_AFTER_SAVE) {
                assertEquals(user.getId(), usersRepository.findById(user.getId()).get().getId());
                assertEquals(user.getEmail(), usersRepository.findById(user.getId()).get().getEmail());
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void update() {
        usersRepository.update(USER_FOR_UPDATE);
        assertEquals(USER_FOR_UPDATE.getId(), usersRepository.findById(USER_FOR_UPDATE.getId()).get().getId());
        assertEquals(USER_FOR_UPDATE.getEmail(), usersRepository.findById(USER_FOR_UPDATE.getId()).get().getEmail());
    }

    @Test
    void delete() {
        usersRepository.delete(ID_USER_FOR_DELETE);
        if (usersRepository.findById(ID_USER_FOR_DELETE).isPresent()) {
            assertEquals(Optional.empty(), (User) usersRepository.findById(ID_USER_FOR_DELETE).get());
        }
    }

    private static void creatTableUsers(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_USERS);
            statement.execute(CREATE_TABLE_USERS);
        }
    }

    private static void saveSomeUsers(UsersRepository<User> usersRepository) throws IllegalAccessException {
        for (String s : ARRAY_EMAIL) {
            usersRepository.save(new User(null, s, null));
        }
    }

}
