package edu.lieineyes.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

public class EmbeddedDataSourceTest {

    private DataSource db = null;

    @BeforeEach
    void init() {
        db = new EmbeddedDatabaseBuilder()
                .setName("HyperSQL")
                .setType(HSQL)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScripts("schema.sql", "data.sql")
                .build();
    }

    @Test
    void isConnection() {
        try (Connection connection = db.getConnection()) {
            assertNotNull(connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
