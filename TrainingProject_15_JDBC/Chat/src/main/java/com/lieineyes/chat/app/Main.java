package com.lieineyes.chat.app;

import com.lieineyes.chat.exception.NotSavedSubEntityException;
import com.lieineyes.chat.models.*;
import com.lieineyes.chat.repositories.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final String PATH_DATA_SOURCE_CONFIG = "TrainingProject_15_JDBC/Chat/src/main/resources/datasource.config";   // для запуска из idea
//    private static final String PATH_DATA_SOURCE_CONFIG = "target/classes/datasource.config";                       // для запуска из терминала

    public static void main(String[] args) {

        HikariDataSource dataSource = new HikariDataSource(new HikariConfig(PATH_DATA_SOURCE_CONFIG));

        MessagesRepositoryJdbcImpl test_message = new MessagesRepositoryJdbcImpl(dataSource);
        UsersRepositoryJdbcImpl test_users = new UsersRepositoryJdbcImpl(dataSource);

        System.out.println("\n======>>> TEST findById message <<<======");
        test1(test_message);
        System.out.println("\n======>>> TEST save message <<<======");
        test2(test_message);
        System.out.println("\n======>>> TEST update message <<<======");
        test3(test_message);
        System.out.println("\n======>>> TEST findAll users <<<======");
        test4(test_users);
    }

    private static void test1(MessagesRepositoryJdbcImpl test_message) {
        System.out.println("Enter a message ID");
        System.out.println("-> ");
        Scanner sc = new Scanner(System.in);
        Optional<Message> byId = test_message.findById(sc.nextInt());
        byId.ifPresent(message -> System.out.println(message.toString()));
    }

    private static void test2(MessagesRepositoryJdbcImpl test_message) {
        User creator1 = new User(3L, "test1", "qwerty", new ArrayList<>(), new ArrayList<>());
        User author1 = creator1;
        Chatroom room1 = new Chatroom(2L, "test1", creator1, new ArrayList<>());
        Message message1 = new Message(null, author1, room1, "Привет", LocalDateTime.now());
        try {
            test_message.save(message1);
            System.out.println(message1.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e);
        }

        User creator2 = new User(15L, "test1", "qwerty", new ArrayList<>(), new ArrayList<>());
        User author2 = creator2;
        Chatroom room2 = new Chatroom(2L, "test1", creator2, new ArrayList<>());
        Message message2 = new Message(null, author2, room2, "Привет", LocalDateTime.now());
        try {
            test_message.save(message2);
            System.out.println(message2.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e);
        }

        User creator3 = new User(3L, "test1", "qwerty", new ArrayList<>(), new ArrayList<>());
        User author3 = creator3;
        Chatroom room3 = new Chatroom(15L, "test1", creator3, new ArrayList<>());
        Message message3 = new Message(null, author3, room3, "Привет", LocalDateTime.now());
        try {
            test_message.save(message3);
            System.out.println(message3.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e);
        }
    }

    private static void test3(MessagesRepositoryJdbcImpl test_message) {
        Optional<Message> messageOptional = test_message.findById(4);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setAuthor(null);
            message.setText("Bye");
            message.setDate(null);
            test_message.update(message);
            System.out.println("update success");
        }
    }

    private static void test4(UsersRepositoryJdbcImpl test_users) {
        List<User> users = test_users.findAll(1, 2);
        for(User user : users) {
            System.out.println(user);
        }
    }

}
