package edu.lieineyes.sockets.repositories.messages;

import edu.lieineyes.sockets.models.Chatroom;
import edu.lieineyes.sockets.models.Message;
import edu.lieineyes.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;


@Component
public class MessageRepositoryImpl implements MessageRepository {

    private static final String NAME_BEAN_FOR_DATASOURCE = "hikariDataSource";

    private static final String SEARCH_MESSAGE_BY_AUTHOR_ID = "SELECT m.id AS message_id,\n" +
            "       um.id AS message_author_id,\n" +
            "       um.login AS message_author_login,\n" +
            "       um.password AS message_author_password,\n" +
            "       ch.id AS room_id,\n" +
            "       ch.name AS room_name,\n" +
            "       uch.id AS room_owner_id,\n" +
            "       uch.login AS room_owner_login,\n" +
            "       uch.password AS room_owner_password,\n" +
            "       m.message AS message,\n" +
            "       m.date AS date\n" +
            "FROM (SELECT * FROM messages WHERE author_id = ?) AS m\n" +
            "LEFT JOIN users AS um ON m.author_id = um.id\n" +
            "LEFT JOIN chatroom AS ch ON m.room_id = ch.id\n" +
            "LEFT JOIN users AS uch ON ch.owner_id = uch.id\n" +
            "ORDER BY m.id \n;";

    private static final String SEARCH_MESSAGE_BY_ROOM_ID = "SELECT m.id AS message_id,\n" +
            "       um.id AS message_author_id,\n" +
            "       um.login AS message_author_login,\n" +
            "       um.password AS message_author_password,\n" +
            "       ch.id AS room_id,\n" +
            "       ch.name AS room_name,\n" +
            "       uch.id AS room_owner_id,\n" +
            "       uch.login AS room_owner_login,\n" +
            "       uch.password AS room_owner_password,\n" +
            "       m.message AS message,\n" +
            "       m.date AS date\n" +
            "FROM (SELECT * FROM messages WHERE room_id = ?) AS m\n" +
            "LEFT JOIN users AS um ON m.author_id = um.id\n" +
            "LEFT JOIN chatroom AS ch ON m.room_id = ch.id\n" +
            "LEFT JOIN users AS uch ON ch.owner_id = uch.id\n" +
            "ORDER BY m.id \n;";

    private static final String SEARCH_LAST_SOME_MESSAGES_BY_ROOM_NAME = "SELECT m.id AS message_id,\n" +
            "       um.id AS message_author_id,\n" +
            "       um.login AS message_author_login,\n" +
            "       um.password AS message_author_password,\n" +
            "       ch.id AS room_id,\n" +
            "       ch.name AS room_name,\n" +
            "       uch.id AS room_owner_id,\n" +
            "       uch.login AS room_owner_login,\n" +
            "       uch.password AS room_owner_password,\n" +
            "       m.message AS message,\n" +
            "       m.date AS date\n" +
            "FROM (SELECT * FROM messages ORDER BY id DESC LIMIT ?) AS m\n" +
            "LEFT JOIN users AS um ON m.author_id = um.id\n" +
            "LEFT JOIN chatroom AS ch ON m.room_id = ch.id\n" +
            "LEFT JOIN users AS uch ON ch.owner_id = uch.id\n" +
            "WHERE ch.name = ?\n" +
            "ORDER BY m.id \n;";

    private static final String SEARCH_MESSAGE_BY_ID = "SELECT m.id AS message_id,\n" +
            "       um.id AS message_author_id,\n" +
            "       um.login AS message_author_login,\n" +
            "       um.password AS message_author_password,\n" +
            "       ch.id AS room_id,\n" +
            "       ch.name AS room_name,\n" +
            "       uch.id AS room_owner_id,\n" +
            "       uch.login AS room_owner_login,\n" +
            "       uch.password AS room_owner_password,\n" +
            "       m.message AS message,\n" +
            "       m.date AS date\n" +
            "FROM (SELECT * FROM messages WHERE id = ?) AS m\n" +
            "LEFT JOIN users AS um ON m.author_id = um.id\n" +
            "LEFT JOIN chatroom AS ch ON m.room_id = ch.id\n" +
            "LEFT JOIN users AS uch ON ch.owner_id = uch.id\n";

    private static final String SEARCH_MESSAGE_BY_ALL = "SELECT m.id AS message_id,\n" +
            "       um.id AS message_author_id,\n" +
            "       um.login AS message_author_login,\n" +
            "       um.password AS message_author_password,\n" +
            "       ch.id AS room_id,\n" +
            "       ch.name AS room_name,\n" +
            "       uch.id AS room_owner_id,\n" +
            "       uch.login AS room_owner_login,\n" +
            "       uch.password AS room_owner_password,\n" +
            "       m.message AS message,\n" +
            "       m.date AS date\n" +
            "FROM  messages AS m\n" +
            "LEFT JOIN users AS um ON m.author_id = um.id\n" +
            "LEFT JOIN chatroom AS ch ON m.room_id = ch.id\n" +
            "LEFT JOIN users AS uch ON ch.owner_id = uch.id\n" +
            "ORDER BY m.id \n;";

    private static final String FIND_LAST_ROW = "SELECT m.id AS message_id,\n" +
            "       um.id AS message_author_id,\n" +
            "       um.login AS message_author_login,\n" +
            "       um.password AS message_author_password,\n" +
            "       ch.id AS room_id,\n" +
            "       ch.name AS room_name,\n" +
            "       uch.id AS room_owner_id,\n" +
            "       uch.login AS room_owner_login,\n" +
            "       uch.password AS room_owner_password,\n" +
            "       m.message AS message,\n" +
            "       m.date AS date\n" +
            "FROM (SELECT * FROM messages ORDER BY id DESC LIMIT 1) AS m\n" +
            "LEFT JOIN users AS um ON m.author_id = um.id\n" +
            "LEFT JOIN chatroom AS ch ON m.room_id = ch.id\n" +
            "LEFT JOIN users AS uch ON ch.owner_id = uch.id\n" +
            "ORDER BY m.id \n;";

    private static final RowMapper<Message> MESSENGER_ROW_MAPPER = (ResultSet resultSet, int rowNum)
            -> new Message(resultSet.getLong("message_id"),
            new User(resultSet.getLong("message_author_id"), resultSet.getString("message_author_login"), resultSet.getString("message_author_password")),
            new Chatroom(resultSet.getLong("room_id"), resultSet.getString("room_name"),
                    new User(resultSet.getLong("room_owner_id"), resultSet.getString("room_owner_login"), resultSet.getString("room_owner_password"))),
            resultSet.getString("message"),
            resultSet.getTimestamp("date").toLocalDateTime());

    private static final String SAVE_MESSAGE = "INSERT INTO messages (author_id, room_id, message, date) VALUES (?, ?, ?, ?);";

    private static final String UPDATE_MESSAGE = "UPDATE messages SET author_id = ?, room_id = ?, message = ?, date = ? WHERE id = ?;";

    private static final String DELETE_MESSAGE = "DELETE FROM messages WHERE id=?;";

    private static final String COLUMN_ID = "id";


    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public MessageRepositoryImpl(@Qualifier(NAME_BEAN_FOR_DATASOURCE) DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Message> findByAuthorId(Long authorId) {
        return this.jdbcTemplate.query(SEARCH_MESSAGE_BY_AUTHOR_ID, MESSENGER_ROW_MAPPER, authorId);
    }

    @Override
    public List<Message> findByRoomId(Long roomId) {
        return this.jdbcTemplate.query(SEARCH_MESSAGE_BY_ROOM_ID, MESSENGER_ROW_MAPPER, roomId);
    }


    @Override
    public List<Message> findLastSomeMessagesByNameRoom(String nameRoom, Long countMessage) {
        return this.jdbcTemplate.query(SEARCH_LAST_SOME_MESSAGES_BY_ROOM_NAME, MESSENGER_ROW_MAPPER, countMessage, nameRoom);
    }

    @Override
    public Optional<Message> findById(Long id) {
        return this.jdbcTemplate.query(SEARCH_MESSAGE_BY_ID, MESSENGER_ROW_MAPPER, id)
                .stream()
                .findAny();
    }

    @Override
    public List<Message> findAll() {
        return this.jdbcTemplate.query(SEARCH_MESSAGE_BY_ALL, MESSENGER_ROW_MAPPER);
    }

    @Override
    public void save(Message entity) throws RuntimeException {
        this.jdbcTemplate.update(SAVE_MESSAGE,
                entity.getAuthor().getId(),
                entity.getChatroom().getId(),
                entity.getMessage(),
                entity.getDate());

        Message message = this.jdbcTemplate.query(FIND_LAST_ROW, MESSENGER_ROW_MAPPER)
                .stream()
                .findAny()
                .get();
        try {
            Field field = entity.getClass().getDeclaredField(COLUMN_ID);
            field.setAccessible(true);
            field.set(entity, message.getId());
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Message entity) {
        this.jdbcTemplate.update(UPDATE_MESSAGE,
                entity.getAuthor(),
                entity.getMessage(),
                entity.getDate(),
                entity.getId());
    }

    @Override
    public void delete(Long id) {
        this.jdbcTemplate.update(DELETE_MESSAGE, id);
    }

}
