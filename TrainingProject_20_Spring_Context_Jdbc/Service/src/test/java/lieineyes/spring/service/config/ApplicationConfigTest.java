package lieineyes.spring.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationConfigTest {

    private ApplicationContext context;

    private DataSource dataSource;

    @BeforeEach
    void init() {
        context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    }

    @Test
    void isHikariConfig() {
        assertNotNull(context.getBean("hikariConfig", HikariConfig.class));
    }

    @Test
    void isHikariDataSource() {
        assertNotNull(context.getBean("hikariDataSource", HikariDataSource.class));
    }

}
