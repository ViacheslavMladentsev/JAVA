Необходимо создать некоторый функционал чата. В этом чате пользователь может создать или выбрать существующий чат-рум. В каждом чате может быть несколько пользователей, которые обмениваются сообщениями.
Моделями предметной области, для которых должны быть реализованы как таблицы SQL, так и классы Java, являются:
- User
  - User ID
  - Login
  -	Password
  -	List of created rooms
  -	List of chatrooms where a user socializes
- Chatroom
  -	Chatroom id
  - Chatroom name
  - Chatroom owner
  - List of messages in a chatroom
- Message
  - Message id
  - Message author
  - Message room
  - Message text
  - Message date/time

Создайте файл schema.sql, в котором напишите инструкции для создания таблиц проекта. Также необходимо создать файл data.sql с текстовыми вставками данных и частично наполнить таблицы.

Additional requirements:

- Для реализации реляционных связей используйте типы связей "один ко многим" и "многие ко многим".
- Идентификаторы должны быть числовыми.
- Идентификаторы должны генерироваться СУБД.
- equals(), hashCode() и toString() должны быть корректно переопределены внутри классов Java.


Необходимо использовать шаблон проектирования Data Access Object (DAO, Repository).
Создайте интерфейс MessagesRepository и определите в нём метод Optional<Message> findById(Long id), а его реализацию в классе MessagesRepositoryJdbcImpl.  
Этот метод должен возвращать объект сообщения, в котором будут указаны автор и чат-комната. В свою очередь, нет необходимости вводить дополнительные данные (список чатов, создатель чата и т.д.) для автора и чата.
MessagesRepositoryJdbcImpl должен принимать интерфейс источника данных пакета java.sql в качестве параметра конструктора.
Для реализации DataSource используйте библиотеку HikariCP.
Протестируйте код в Program.java.
Пример работы программы:
```
$ java Program
Enter a message ID
-> 5
Message : {
  id=5,
  author={id=7,login=“user”,password=“user”,createdRooms=null,rooms=null},
  room={id=8,name=“room”,creator=null,messages=null},
  text=“message”,
  dateTime=01/01/01 15:69
}
```

Необходимо реализовать save(Message message).

Таким образом, нам нужно определить следующие подразделы для объекта, который мы сохраняем — автора сообщения и комнату чата. Также важно присвоить комнате чата и автору идентификаторы, существующие в базе данных.
Метод save должен присвоить значение ID для входящей модели после сохранения данных в базе данных. Если author и room не имеют присвоенных идентификаторов в базе данных или эти идентификаторы равны нулю, вызовите Runtimeexception NotSavedSubEntityException (реализуйте это исключение самостоятельно).
Протестируйте код в Program.java.

Пример использования программы:
```java
public static void main(String args[]) {
	...
  User creator = new User(7L, "user", "user", new ArrayList(), new ArrayList());
  User author = creator;
  Room room = new Room(8L, "room", creator, new ArrayList());
  Message message = new Message(null, author, room, "Hello!", LocalDateTime.now());
  MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(...);
  messagesRepository.save(message);
  System.out.println(message.getId()); // ex. id == 11
}
```

Необходимо реализовать метод обновления в MessageRepository. Этот метод должен полностью обновить существующую сущность в базе данных. Если новое значение поля в обновляемой сущности равно null, это значение должно быть сохранено в базе данных.

Пример использования программы:
```java
public static void main(String args[]) {
  MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(...);
  Optional<Message> messageOptional = messagesRepository.findById(11);
  if (messageOptinal.isPresent()) {
    Message message = messageOptional.get();
    message.setText("Bye");
    message.setDateTime(null);
    messagesRepository.update(message);
  }
  ...
}
```

Необходимо реализовать интерфейс UsersRepository и определить в нём метод List<Пользователь> findAll(int page, int size). Реализовать интерфейс необходимо в классе UsersRepositoryJdbcImpl.
UsersRepositoryJdbcImpl должен принимать интерфейс источника данных пакета java.sql в качестве параметра конструктора. Этот метод должен возвращать пользователей, отображаемых на странице, с указанием номера страницы. Таким образом, СУБД делит общий набор на страницы, каждая из которых содержит записи о размере. Например, если набор содержит 20 записей с page = 3 и size = 4, вы получаете пользователей от 12 до 15 (нумерация пользователей и страниц начинается с 0).
Метод findAll (int page, int size) должен быть реализован ОДНИМ запросом к базе данных. Не допускается использование дополнительных SQL-запросов для получения информации по каждому пользователю.

Каждый пользователь в результирующем списке должен иметь включенные зависимости — список чатов, созданных этим пользователем, а также список чатов, в которых участвует пользователь.
Каждый подраздел пользователя НЕ ДОЛЖЕН включать свои зависимости, т.е. Список сообщений внутри каждой комнаты должен быть пустым.
Протестируйте код в Program.java.

**Notes**:
- findAll(int page, int size) method shall be implemented by a SINGLE database query. It is not allowed to use additional SQL queries to retrieve information for each user.
- We recommend using CTE PostgreSQL.
- UsersRepositoryJdbcImpl shall accept DataSource interface of java.sql package as a constructor parameter.


Структура проекта:
- Chat
  - src
    - main
      - java
        - edu.lieineyes.chat
          - models
            - ... 
          - repositories
            - ... 
          - exception
            - ... 
          - app
            - Main.java
      - resources
        - schema.sql
        - data.sql
  - pom.xml
