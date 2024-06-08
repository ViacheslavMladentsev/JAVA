package com.lieineyes.chat.repositories;

import com.lieineyes.chat.exception.NotSavedSubEntityException;
import com.lieineyes.chat.models.*;

import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@AllArgsConstructor
public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private static final String SEARCH_QWERY_BY_ID =
            "SELECT msg.id AS id,\n" +
                    "author_id,\n" +
                    "login,\n" +
                    "password,\n" +
                    "room_id,\n" +
                    "name,\n" +
                    "owner_id,\n" +
                    "message,\n" +
                    "date\n" +
                    "FROM message AS msg\n" +
                    "    LEFT JOIN \"user\" AS u ON msg.author_id = u.id\n" +
                    "    LEFT JOIN chatroom AS ch ON msg.room_id = ch.id\n" +
                    "WHERE msg.id=?";

    private static final String SAVE_MESSAGE = "INSERT INTO message(author_id, room_id, message, date)\n" +
            "VALUES (?, ?, ?, ?);";

    private static final String UPDATE_MESSAGE = "UPDATE message SET author_id = ?," +
            "                                                       room_id = ?," +
            "                                                       message = ?," +
            "                                                       date = ?" +
            "                                     WHERE id = ?;";

    private static final String AUTHOR_ID = "author_id";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";
    private static final String ROOM_ID = "room_id";
    private static final String ROOM_NAME = "name";
    private static final String MESSAGE_ID = "id";
    private static final String MESSAGE_TEXT = "message";
    private static final String MESSAGE_DATE = "date";
    private static final String ID = "id";

    private final DataSource dataSource;


    @Override
    public Optional<Message> findById(long id) {
        Optional<Message> result = Optional.empty();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_QWERY_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(resultSet.getLong(AUTHOR_ID),
                            resultSet.getString(USER_LOGIN),
                            resultSet.getString(USER_PASSWORD),
                            null,
                            null);

                    Chatroom chatroom = new Chatroom(resultSet.getLong(ROOM_ID),
                            resultSet.getString(ROOM_NAME),
                            null,
                            null);

                    Message message = new Message(resultSet.getLong(MESSAGE_ID),
                            user,
                            chatroom,
                            resultSet.getString(MESSAGE_TEXT),
                            resultSet.getTimestamp(MESSAGE_DATE).toLocalDateTime());
                    result = Optional.of(message);
                }
                return result;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return result;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return result;
        }
    }

    @Override
    public void save(Message msg) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_MESSAGE, Statement.RETURN_GENERATED_KEYS)) {

                initializationStatementForSave(statement, msg);
                statement.execute();

                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        msg.setId(resultSet.getLong(ID));
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

        } catch (NotSavedSubEntityException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void initializationStatementForSave(PreparedStatement statement, Message msg) throws SQLException {
        statement.setLong(1, msg.getAuthor().getId());
        statement.setLong(2, msg.getChatroom().getId());
        statement.setString(3, msg.getText());
        statement.setTimestamp(4, Timestamp.valueOf(msg.getDate()));
    }

    @Override
    public void update(Message msg) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_MESSAGE)) {

                initializationStatementForUpdate(statement, msg);
                statement.setLong(5, msg.getId());
                statement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void initializationStatementForUpdate(PreparedStatement statement, Message msg) throws SQLException {
        if (msg.getAuthor() == null) {
            statement.setNull(1, Types.INTEGER);
        } else {
            statement.setLong(1, msg.getAuthor().getId());
        }

        if (msg.getChatroom() == null) {
            statement.setNull(2, Types.INTEGER);
        } else {
            statement.setLong(2, msg.getChatroom().getId());
        }

        if (msg.getText() == null) {
            statement.setNull(3, Types.INTEGER);
        } else {
            statement.setString(3, msg.getText());
        }

        if (msg.getDate() == null) {
            statement.setNull(4, Types.TIMESTAMP);
        } else {
            statement.setTimestamp(4, Timestamp.valueOf(msg.getDate()));
        }
    }

}
