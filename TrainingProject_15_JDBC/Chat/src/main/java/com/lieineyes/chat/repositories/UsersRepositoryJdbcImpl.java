package com.lieineyes.chat.repositories;

import com.lieineyes.chat.models.*;

import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@AllArgsConstructor
public class UsersRepositoryJdbcImpl implements UsersRepository {

    private static final String SQL_FIND_ALL_USER_ON_PAGE =
            "WITH needed_list_users AS\n" +
                    "         (SELECT id       AS user_id,\n" +
                    "                 login    AS user_login,\n" +
                    "                 password AS user_password\n" +
                    "          FROM \"user\"\n" +
                    "          LIMIT ? OFFSET ? * ?),\n" +
                    "     users_created_rooms AS\n" +
                    "         (SELECT nlu.*,\n" +
                    "                 ch.id   AS chatroom_id,\n" +
                    "                 ch.name AS chatroom_name,\n" +
                    "                 ch.owner_id,\n" +
                    "                 0       AS author_id\n" +
                    "          FROM needed_list_users AS nlu\n" +
                    "                   LEFT JOIN chatroom AS ch ON nlu.user_id = ch.owner_id),\n" +
                    "     users_participant_rooms AS\n" +
                    "         (SELECT nlu.*,\n" +
                    "                 ch.id       AS chatroom_id,\n" +
                    "                 ch.name     AS chatroom_name,\n" +
                    "                 ch.owner_id,\n" +
                    "                 m.author_id AS author_id\n" +
                    "          FROM needed_list_users AS nlu\n" +
                    "                   LEFT JOIN message AS m ON nlu.user_id = m.author_id\n" +
                    "                   LEFT JOIN chatroom AS ch ON m.room_id = ch.id),\n" +
                    "     users AS\n" +
                    "         (SELECT *\n" +
                    "          FROM users_created_rooms\n" +
                    "          UNION\n" +
                    "          SELECT *\n" +
                    "          FROM users_participant_rooms)\n" +
                    "SELECT us.*,\n" +
                    "       u.login AS owner_login,\n" +
                    "       u.password AS owner_password\n" +
                    "FROM users AS us\n" +
                    "LEFT JOIN \"user\" AS u ON us.owner_id = u.id\n" +
                    "WHERE us.chatroom_id IS NOT NULL;";

    private static final String USER_ID = "user_id";
    private static final String USER_LOGIN = "user_login";
    private static final String USER_PASSWORD = "user_password";
    private static final String CHATROOM_ID = "chatroom_id";
    private static final String CHATROOM_NAME = "chatroom_name";
    private static final String OWNER_ID = "owner_id";
    private static final String OWNER_LOGIN = "owner_login";
    private static final String OWNER_PASSWORD = "owner_password";
    private static final String AUTHOR_ID = "author_id";

    private final DataSource dataSource;


    @Override
    public List<User> findAll(int page, int size) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USER_ON_PAGE)) {

            statement.setInt(1, size);
            statement.setInt(2, page);
            statement.setInt(3, size);

            try (ResultSet resultSet = statement.executeQuery()) {
                HashMap<Long, User> mapUsers = new HashMap<>();

                while (resultSet.next()) {
                    Long userID = resultSet.getLong(USER_ID);
                    if (!mapUsers.containsKey(userID)) {
                        mapUsers.put(userID, new User(userID,
                                resultSet.getString(USER_LOGIN),
                                resultSet.getString(USER_PASSWORD),
                                new ArrayList<>(),
                                new ArrayList<>()));
                    }
                    User user = mapUsers.get(userID);
                    List<Chatroom> arrayChatroom;
                    if (userID != resultSet.getLong(AUTHOR_ID)) {
                        arrayChatroom = user.getCreatedRooms();
                    } else {
                        arrayChatroom = user.getChatroomsSocializes();
                    }

                    arrayChatroom.add(new Chatroom(resultSet.getLong(CHATROOM_ID),
                            resultSet.getString(CHATROOM_NAME),
                            new User(resultSet.getLong(OWNER_ID),
                                    resultSet.getString(OWNER_LOGIN),
                                    resultSet.getString(OWNER_PASSWORD),
                                    null, null),
                            null));
                }
                return new ArrayList<>(mapUsers.values());

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return Collections.emptyList();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

}
