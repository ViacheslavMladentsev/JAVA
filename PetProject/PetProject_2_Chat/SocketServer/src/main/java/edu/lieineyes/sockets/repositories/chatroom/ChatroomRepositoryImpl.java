package edu.lieineyes.sockets.repositories.chatroom;

import edu.lieineyes.sockets.models.Chatroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Component
public class ChatroomRepositoryImpl implements ChatroomRepository {

    private static final String NAME_BEAN_FOR_DATASOURCE = "hikariDataSource";

    private static final String SEARCH_CHATROOM_BY_NAMEROOM = "SELECT * FROM chatroom WHERE name=?;";

    private static final String SEARCH_CHATROOM_BY_ID = "SELECT * FROM chatroom WHERE id=?";

    private static final String SEARCH_ALL_NAME_ROOMS = "SELECT name FROM chatroom";

    private static final String SEARCH_CHATROOM_BY_ALL = "SELECT * FROM chatroom";

    private static final String SAVE_CHATROOM = "INSERT INTO chatroom (name, owner_id) VALUES (?, ?);";

    private static final String UPDATE_CHATROOM = "UPDATE chatroom SET name = ?, owner_id = ? WHERE id = ?;";

    private static final String DELETE_CHATROOM = "DELETE FROM chatroom WHERE id=?;";

    private static final String FIND_LAST_ROW = "SELECT * FROM chatroom ORDER BY id DESC LIMIT 1";

    private static final String COLUMN_ID = "id";


    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public ChatroomRepositoryImpl(@Qualifier(NAME_BEAN_FOR_DATASOURCE) DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Optional<Chatroom> findByName(String nameRoom) {
        return this.jdbcTemplate.query(SEARCH_CHATROOM_BY_NAMEROOM,
                        new Object[]{nameRoom},
                        new BeanPropertyRowMapper<>(Chatroom.class))
                .stream()
                .findAny();
    }

    @Override
    public List<Chatroom> findAllNameRoom() {
        return this.jdbcTemplate.query(SEARCH_ALL_NAME_ROOMS, new BeanPropertyRowMapper<>(Chatroom.class));
    }

    @Override
    public Optional<Chatroom> findById(Long id) {
        return this.jdbcTemplate.query(SEARCH_CHATROOM_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Chatroom.class))
                .stream()
                .findAny();
    }

    @Override
    public List<Chatroom> findAll() {
        return this.jdbcTemplate.query(SEARCH_CHATROOM_BY_ALL, new BeanPropertyRowMapper<>(Chatroom.class));
    }

    @Override
    public void save(Chatroom entity) throws RuntimeException {
        this.jdbcTemplate.update(SAVE_CHATROOM,
                entity.getName(),
                entity.getOwner().getId());

        Chatroom chatroom = this.jdbcTemplate.query(FIND_LAST_ROW, new BeanPropertyRowMapper<>(Chatroom.class))
                .stream()
                .findAny()
                .get();
        try {
            Field field = entity.getClass().getDeclaredField(COLUMN_ID);
            field.setAccessible(true);
            field.set(entity, chatroom.getId());
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Chatroom entity) {
        this.jdbcTemplate.update(UPDATE_CHATROOM, entity.getName(), entity.getOwner().getId(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        this.jdbcTemplate.update(DELETE_CHATROOM, id);
    }

}
