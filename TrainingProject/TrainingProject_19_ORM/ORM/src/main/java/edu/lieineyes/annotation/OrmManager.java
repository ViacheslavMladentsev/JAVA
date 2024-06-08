package edu.lieineyes.annotation;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;

import java.util.Set;

public class OrmManager {

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    private static final String INSERT_INTO = "INSERT INTO ";
    private static final String VALUES = "VALUES ";
    private static final String SELECT_VALUE_LAST_ROW =
            "SELECT %s FROM %s OFFSET (SELECT count(*) FROM %s) - 1 LIMIT (SELECT count(*) FROM %s);";
    private static final String SELECT_ENITY_BY_ID =
            "SELECT * FROM %s WHERE id = %d";

    private static final String PRIMARY_KEY = "serial PRIMARY KEY";
    private static final String DEFAULT_NAME_COLUMN = "\"column\"";

    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static final String END_SQL_REQUEST = ";";
    private static final String DELIMITER = ",";
    private static final String SPACE = " ";
    private static final String QUOTATION_MARK = "'";
    private static final String ASSIGN = "=";

    private static final String MESSAGE_NOT_EXSITS_TABLE = "Указанная таблица отсутствует в БД ";
    private static final String MESSAGE_NOT_EXSITS_COLUMN = "Столбец 'id' отсутствует в таблице ";


    private static DataSource dataSource;

    private static Set<Class> entityClasses;


    public OrmManager(DataSource dataSource, Set<Class> entityClasses) throws IllegalArgumentException, SQLException {
        OrmManager.dataSource = dataSource;
        OrmManager.entityClasses = entityClasses;
        createTable();
    }


    private static void createTable() throws IllegalArgumentException, SQLException {
        for (Class<?> aClass : entityClasses) {
            String tableName = null;
            if (aClass.isAnnotationPresent(OrmEntity.class)) {
                tableName = aClass.getDeclaredAnnotation(OrmEntity.class).table();
            }

            StringBuilder createTable = new StringBuilder(CREATE_TABLE + tableName + OPEN_BRACKET);
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    String tempFieldName = field.getAnnotation(OrmColumnId.class).name();
                    createTable.append(tempFieldName.isEmpty() ? DEFAULT_NAME_COLUMN : tempFieldName)
                            .append(SPACE).append(PRIMARY_KEY).append(DELIMITER);
                }
                if (field.isAnnotationPresent(OrmColumn.class)) {
                    String tempFieldName = field.getAnnotation(OrmColumn.class).name();
                    createTable.append(tempFieldName.isEmpty() ? DEFAULT_NAME_COLUMN : tempFieldName)
                            .append(SPACE).append(switchType(aClass, field)).append(DELIMITER);
                }
            }
            createTable.delete(createTable.length() - 1, createTable.length());
            createTable.append(CLOSE_BRACKET).append(END_SQL_REQUEST);

            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.execute(DROP_TABLE + tableName);
                System.out.println(DROP_TABLE + tableName);
                statement.execute(String.valueOf(createTable));
                System.out.println(createTable);

            }
        }
    }


    public void save(Object entity) throws IllegalAccessException, SQLException {
        StringBuilder insertString = new StringBuilder(INSERT_INTO);
        String tableName = getTableNameByObject(entity);

        insertString.append(tableName).append(OPEN_BRACKET);
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumn.class)) {
                insertString.append(field.getDeclaredAnnotation(OrmColumn.class).name()).append(DELIMITER);
            }
        }
        insertString.delete(insertString.length() - 1, insertString.length());
        insertString.append(CLOSE_BRACKET).append(SPACE).append(VALUES).append(OPEN_BRACKET);

        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumn.class)) {
                field.setAccessible(true);
                if (field.getType() == String.class && field.get(entity) != null) {
                    insertString.append(QUOTATION_MARK).append(field.get(entity)).append(QUOTATION_MARK).append(DELIMITER);
                } else {
                    insertString.append(field.get(entity)).append(DELIMITER);
                }
                field.setAccessible(false);
            }
        }
        insertString.delete(insertString.length() - 1, insertString.length());
        insertString.append(CLOSE_BRACKET).append(END_SQL_REQUEST);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(String.valueOf(insertString));
            System.out.println(insertString);
            recordSequenceValueInModelClassAfterSave(statement, fields, entity);
        }

    }


    public <T> T findById(Long id, Class<T> aClass) throws SQLException, InstantiationException, IllegalAccessException {
        String tableName = aClass.getDeclaredAnnotation(OrmEntity.class).table();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            if (tableExists(connection, tableName)) {
                if (columnExists(connection, tableName, "id")) {
                    ResultSet resultSet = statement.executeQuery(String.format(SELECT_ENITY_BY_ID, tableName, id));
                    if (resultSet.next()) {
                        T newObject = aClass.newInstance();
                        Field[] fields = aClass.getDeclaredFields();
                        setterAllFields(fields, resultSet, tableName, newObject);
                        return newObject;
                    } else {
                        return null;
                    }
                } else {
                    throw new SQLException(MESSAGE_NOT_EXSITS_COLUMN + tableName);
                }
            } else {
                throw new SQLException(MESSAGE_NOT_EXSITS_TABLE + tableName);
            }
        }
    }


    public void update(Object entity) throws NoSuchFieldException, IllegalAccessException, SQLException {
        String tableName = entity.getClass().getDeclaredAnnotation(OrmEntity.class).table();
        StringBuilder update = new StringBuilder("UPDATE " + tableName + " SET ");
        Object id = null;
        String idName = "";
        Field[] fields = entity.getClass().getDeclaredFields();
        System.out.println("size = " + fields.length);
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                id = field.get(entity);
                idName = field.getAnnotation(OrmColumnId.class).name();
            } else if (field.isAnnotationPresent(OrmColumn.class)) {
                if (field.getType() == String.class) {
                    update.append(field.getAnnotation(OrmColumn.class).name())
                            .append(ASSIGN).append(QUOTATION_MARK)
                            .append(field.get(entity))
                            .append(QUOTATION_MARK).append(DELIMITER);
                } else {
                    update.append(field.getAnnotation(OrmColumn.class).name()).append(ASSIGN).append(field.get(entity)).append(DELIMITER);
                }
            }
            field.setAccessible(false);
        }
        update.delete(update.length() - 1, update.length());
        update.append(SPACE).append("WHERE ").append(idName).append(ASSIGN).append(id);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(String.valueOf(update));
            System.out.println(update);
        }

    }


    private static String switchType(Class<?> anyClass, Field field) throws IllegalArgumentException {
        if (field.getType() == String.class) {
            if (field.getName().length() <= field.getAnnotation(OrmColumn.class).length()) {
                return "varchar(" + field.getAnnotation(OrmColumn.class).length() + ")";
            } else {
                throw new IllegalArgumentException("Название поля " + field.getName() +
                        " в классе " + anyClass.getSimpleName() +
                        " превышает установленное ограничение по количеству символов");
            }
        } else if (field.getType() == Integer.class) {
            return "int";
        } else if (field.getType() == Double.class) {
            return "double precision";
        } else if (field.getType() == Boolean.class) {
            return "boolean";
        } else if (field.getType() == Long.class) {
            return "bigint";
        } else {
            throw new IllegalArgumentException("В классе " + anyClass +
                    " используется некорректный тип " + field.getType().getSimpleName() + " в поле " + field.getName());
        }
    }

    private static void recordSequenceValueInModelClassAfterSave(Statement statement, Field[] fields, Object entity) throws SQLException, IllegalAccessException {
        String tableName = getTableNameByObject(entity);
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {

                String select = String.format(SELECT_VALUE_LAST_ROW, field.getAnnotation(OrmColumnId.class).name(), tableName, tableName, tableName);
                ResultSet resultSet = statement.executeQuery(select);

                if (resultSet.next()) {
                    field.setAccessible(true);
                    if (field.getGenericType() == String.class) {
                        field.set(entity, resultSet.getString(field.getAnnotation(OrmColumnId.class).name()));
                    } else if (field.getGenericType() == Integer.class) {
                        field.set(entity, resultSet.getInt(field.getAnnotation(OrmColumnId.class).name()));
                    } else if (field.getGenericType() == Double.class) {
                        field.set(entity, resultSet.getDouble(field.getAnnotation(OrmColumnId.class).name()));
                    } else if (field.getGenericType() == Long.class) {
                        field.set(entity, resultSet.getLong(field.getAnnotation(OrmColumnId.class).name()));
                    } else if (field.getGenericType() == Boolean.class) {
                        field.set(entity, resultSet.getBoolean(field.getAnnotation(OrmColumnId.class).name()));
                    } else {
                        throw new IllegalArgumentException("Используется несовместимый тип " + field.getType().getSimpleName() + " в таблице " + tableName);
                    }
                    field.setAccessible(false);
                }
            }
        }
    }

    private static String getTableNameByObject(Object entity) {
        if (entity.getClass().isAnnotationPresent(OrmEntity.class)) {
            return entity.getClass().getDeclaredAnnotation(OrmEntity.class).table();
        }
        return null;
    }

    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});
        return resultSet.next();
    }

    private static boolean columnExists(Connection connection, String tableName, String columnName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getColumns(null, null, tableName, columnName);
        return resultSet.next();
    }

    private static <T> void setterAllFields(Field[] fields, ResultSet resultSet, String tableName, T newObject) throws SQLException, IllegalAccessException {
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getGenericType() == String.class) {
                if (field.isAnnotationPresent(OrmColumn.class)) {
                    field.set(newObject, resultSet.getString(field.getAnnotation(OrmColumn.class).name()));
                } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                    field.set(newObject, resultSet.getString(field.getAnnotation(OrmColumnId.class).name()));
                }
            } else if (field.getGenericType() == Integer.class) {
                if (field.isAnnotationPresent(OrmColumn.class)) {
                    field.set(newObject, resultSet.getInt(field.getAnnotation(OrmColumn.class).name()));
                } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                    field.set(newObject, resultSet.getInt(field.getAnnotation(OrmColumnId.class).name()));
                }
            } else if (field.getGenericType() == Double.class) {
                if (field.isAnnotationPresent(OrmColumn.class)) {
                    field.set(newObject, resultSet.getDouble(field.getAnnotation(OrmColumn.class).name()));
                } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                    field.set(newObject, resultSet.getDouble(field.getAnnotation(OrmColumnId.class).name()));
                }
            } else if (field.getGenericType() == Long.class) {
                if (field.isAnnotationPresent(OrmColumn.class)) {
                    field.set(newObject, resultSet.getLong(field.getAnnotation(OrmColumn.class).name()));
                } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                    field.set(newObject, resultSet.getLong(field.getAnnotation(OrmColumnId.class).name()));
                }
            } else if (field.getGenericType() == Boolean.class) {
                if (field.isAnnotationPresent(OrmColumn.class)) {
                    field.set(newObject, resultSet.getBoolean(field.getAnnotation(OrmColumn.class).name()));
                } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                    field.set(newObject, resultSet.getBoolean(field.getAnnotation(OrmColumnId.class).name()));
                }
            } else {
                throw new IllegalArgumentException("Используется несовместимый тип " + field.getType().getSimpleName() + " в таблице " + tableName);
            }
            field.setAccessible(false);
        }
    }

}
