package edu.lieineyes.sockets.app;

import com.beust.jcommander.JCommander;
import com.zaxxer.hikari.HikariDataSource;
import edu.lieineyes.sockets.config.SocketsApplicationConfig;
import edu.lieineyes.sockets.server.Server;
import edu.lieineyes.sockets.validator.InputArgs;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {

    private static final String DROP_TABLE_USERS = "DROP TABLE IF EXISTS users;";

    private static final String DROP_TABLE_MESSAGES = "DROP TABLE IF EXISTS messages;";

    private static final String DROP_TABLE_CHATROOM = "DROP TABLE IF EXISTS chatroom;";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS users" +
            "(" +
            "id serial PRIMARY KEY," +
            "login varchar(50) UNIQUE NOT NULL ," +
            "password varchar(60) NOT NULL" +
            ");";

    private static final String CREATE_TABLE_CHATROOM = "CREATE TABLE IF NOT EXISTS chatroom" +
            "(" +
            "id serial PRIMARY KEY," +
            "name varchar(50) UNIQUE NOT NULL," +
            "owner_id integer NOT NULL," +
            "CONSTRAINT chatroom_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES users (id)" +
            ")";

    private static final String CREATE_TABLE_MESSAGES = "CREATE TABLE IF NOT EXISTS messages" +
            "(" +
            "id serial PRIMARY KEY," +
            "author_id integer NOT NULL," +
            "room_id integer NOT NULL," +
            "message text," +
            "date timestamp," +
            "    CONSTRAINT message_author_id_fkey FOREIGN KEY (author_id) REFERENCES users (id),\n" +
            "    CONSTRAINT message_room_id_fkey FOREIGN KEY (room_id) REFERENCES chatroom (id)" +
            ")";

    private static final String NAME_BEAN_HIKARI_DATA_SOURCE = "hikariDataSource";


    public static void main(String[] args) {
        try {
            InputArgs arguments = new InputArgs();
            JCommander.newBuilder().addObject(arguments).build().parse(args);
//            creatTableUsers();
            new Server().start(arguments.getPort());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void creatTableUsers() throws SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        DataSource dataSource = context.getBean(NAME_BEAN_HIKARI_DATA_SOURCE, HikariDataSource.class);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_MESSAGES);
            statement.execute(DROP_TABLE_CHATROOM);
            statement.execute(DROP_TABLE_USERS);
            statement.execute(CREATE_TABLE_USERS);
            statement.execute(CREATE_TABLE_CHATROOM);
            statement.execute(CREATE_TABLE_MESSAGES);
        }
    }

}
