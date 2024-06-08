package lieineyes.spring.service.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import lieineyes.spring.service.models.User;
import lieineyes.spring.service.repositories.UsersRepository;
import lieineyes.spring.service.repositories.UsersRepositoryJdbcImpl;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

class UsersServiceImplTest {

    private static final String EMAIL_FOR_SIGN_UP = "zxc@vbn.qw";

    private DataSource dataSource;

    private UsersRepository<User> usersRepository;

    private UsersService usersService;

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
        usersService = new UsersServiceImpl(usersRepository);
    }

    @AfterEach
    void shootDownDB() {
        if (dataSource != null) {
            ((EmbeddedDatabase) dataSource).shutdown();
        }
    }

    @Test
    void isPassword() {
        assertNotNull(usersService.signUp(EMAIL_FOR_SIGN_UP));
    }

}
