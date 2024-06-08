package lieineyes.spring.service.application;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import lieineyes.spring.service.config.ApplicationConfig;
import lieineyes.spring.service.models.User;
import lieineyes.spring.service.repositories.UsersRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String DROP_TABLE_USERS = "DROP TABLE IF EXISTS users;";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE users (id serial PRIMARY KEY," +
            "email varchar(50)," +
            "password varchar(50));";

    private static final List<String> ARRAY_EMAIL = Arrays.asList("ivan@yandex.ru",
            "petr@gmail.com",
            "nikolay@mail.ru",
            "sergey@ngs.ru",
            "alex@tesla.com");


    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        DataSource dataSource = context.getBean("hikariDataSource", HikariDataSource.class);

        try {
            creatTableUsers(dataSource);
            UsersRepository<User> usersRepository = context.getBean("usersRepositoryJdbcImpl", UsersRepository.class);
            saveSomeUsers(usersRepository);
            System.out.println(usersRepository.findAll());
            usersRepository = context.getBean("usersRepositoryJdbcTemplateImpl", UsersRepository.class);
            System.out.println(usersRepository.findAll());
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
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
