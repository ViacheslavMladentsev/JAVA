package edu.lieineyes.sockets.server;

import com.google.gson.Gson;
import edu.lieineyes.sockets.config.SocketsApplicationConfig;
import edu.lieineyes.sockets.models.Chatroom;
import edu.lieineyes.sockets.models.Message;
import edu.lieineyes.sockets.models.User;
import edu.lieineyes.sockets.repositories.chatroom.ChatroomRepository;
import edu.lieineyes.sockets.repositories.chatroom.ChatroomRepositoryImpl;
import edu.lieineyes.sockets.repositories.messages.MessageRepository;
import edu.lieineyes.sockets.repositories.messages.MessageRepositoryImpl;
import edu.lieineyes.sockets.schemasMessaging.RoomSchema;
import edu.lieineyes.sockets.schemasMessaging.toServer.MessageRoomInfoToServer;
import edu.lieineyes.sockets.schemasMessaging.toServer.MessageToServerBase;
import edu.lieineyes.sockets.schemasMessaging.toServer.MessageUserInfoToServer;
import edu.lieineyes.sockets.schemasMessaging.fromServer.ResponseMessageFromServerBased;
import edu.lieineyes.sockets.schemasMessaging.toServer.ResponseMessageToServerBased;
import edu.lieineyes.sockets.services.chatroom.ChatRoomService;
import edu.lieineyes.sockets.services.chatroom.ChatRoomServiceImpl;
import edu.lieineyes.sockets.services.message.MessageService;
import edu.lieineyes.sockets.services.message.MessageServiceImpl;
import edu.lieineyes.sockets.services.user.UsersService;
import edu.lieineyes.sockets.services.user.UsersServiceImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class SomeClientSocket extends Thread {

    private static final String NAME_BEAN_CHATROOM_REPOSITORY_IMPL = "chatroomRepositoryImpl";

    private static final String NAME_BEAN_MESSAGE_REPOSITORY_IMPL = "messageRepositoryImpl";

    private static final String NAME_BEAN_USERS_SERVICE_IMPL = "usersServiceImpl";

    private static final String NAME_BEAN_CHATROOM_SERVICE_IMPL = "chatRoomServiceImpl";

    private static final String NAME_BEAN_MESSAGE_SERVICE_IMPL = "messageServiceImpl";

    private static final String TEXT_SIGNUP = "signup";

    private static final String TEXT_SIGNIN = "signin";

    private static final String TEXT_EXIT = "exit";

    private static final String TEXT_CREATE_ROOM = "create room";

    private static final String TEXT_CHOOSE_ROOM = "choose room";


    private static final String MESSAGE_STOP_SOCKET_CLIENT = "Socket клиента остановлен!\n";

    private static final String MESSAGE_SERVER_GREETING_FOR_CLIENT = "Hello from Server!\n";

    private static final String NEW_LINE = "\n";

    private static final String MESSAGE_FOR_CLIENT_SIGNUP_SUCCESSFUL = "Регистрация прошла успешно!\n";

    private static final String MESSAGE_ERROR_REGISTRATION_CLIENT = "Ошибка: добавление пользователя с таким логином невозможно. Повторите попытку регистрации\n";

    private static final String MESSAGE_ERROR_AUTHORIZATION_CLIENT = "Ошибка: пользователь отсутствует, либо неверный пароль. Повторите попытку авторизации\n";

    private static final String MESSAGE_FOR_CLIENT_SIGNIN_SUCCESSFUL = "Авторизация прошла успешно!\n";


    private static final String MESSAGE_FOR_CLIENT_CREATE_ROOM_SUCCESSFUL = "Комната успешно создана!\n";

    private static final String MESSAGE_ERROR_CREATE_CHATROOM = "Ошибка: создание комнаты невозможно. Повторите попытку.\n";

    private static final String MESSAGE_ERROR_SELECT_CHATROOM = "Ошибка: неверно введён номер комнаты. Повторите попытку.\n";


    private static final String MESSAGE_EXIT_ROOM = "Вы вышли из комнаты\n";


    private final ChatroomRepository chatroomRepository;

    private final MessageRepository messageRepository;

    private final UsersService<User> usersService;

    private final ChatRoomService<Chatroom> chatRoomService;

    private final MessageService<Message> messageService;

    private final Socket clientSocket;

    private final BufferedReader in;

    private final BufferedWriter out;

    private User currentUser;

    private String nameChatRoom = "";

    private final Gson gson = new Gson();


    public SomeClientSocket(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        this.chatroomRepository = context.getBean(NAME_BEAN_CHATROOM_REPOSITORY_IMPL, ChatroomRepositoryImpl.class);
        this.messageRepository = context.getBean(NAME_BEAN_MESSAGE_REPOSITORY_IMPL, MessageRepositoryImpl.class);
        this.usersService = context.getBean(NAME_BEAN_USERS_SERVICE_IMPL, UsersServiceImpl.class);
        this.chatRoomService = context.getBean(NAME_BEAN_CHATROOM_SERVICE_IMPL, ChatRoomServiceImpl.class);
        this.messageService = context.getBean(NAME_BEAN_MESSAGE_SERVICE_IMPL, MessageServiceImpl.class);
        start();
    }

    @Override
    public void run() {
        try {
            this.out.write(this.gson.toJson(new ResponseMessageFromServerBased(MESSAGE_SERVER_GREETING_FOR_CLIENT)) + NEW_LINE);
            this.out.flush();
            selectStartMenu();
        } catch (IOException | RuntimeException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        } finally {
            this.downService();
        }
    }

    private void downService() {
        try {
            if (!this.clientSocket.isClosed()) {
                this.clientSocket.close();
                this.in.close();
                this.out.close();
                for (SomeClientSocket socket : Server.listSockets) {
                    if (socket.equals(this)) {
                        socket.interrupt();
                    }
                    Server.listSockets.remove(this);
                }
            }
            System.out.println(MESSAGE_STOP_SOCKET_CLIENT);
        } catch (IOException ignored) {
        }
    }


    private void selectStartMenu() throws IOException, RuntimeException, IllegalAccessException {
        myLoop:
        while (true) {
            String clientWord = this.in.readLine();
            MessageToServerBase itemMenu = gson.fromJson(clientWord, MessageToServerBase.class);
            MessageUserInfoToServer messageUserInfo;
            switch (itemMenu.getItemMenu()) {
                case TEXT_SIGNUP:
                    messageUserInfo = gson.fromJson(clientWord, MessageUserInfoToServer.class);
                    registration(messageUserInfo.getUser().getLogin(), messageUserInfo.getUser().getPassword());
                    break;
                case TEXT_SIGNIN:
                    messageUserInfo = gson.fromJson(clientWord, MessageUserInfoToServer.class);
                    authorization(messageUserInfo.getUser().getLogin(), messageUserInfo.getUser().getPassword());
                    selectMenuChatroom();
                    break;
                case TEXT_EXIT:
                    this.downService();
                    break myLoop;
            }
        }
    }


    private void registration(String login, String password) throws IOException, RuntimeException {
        try {
            this.usersService.signUp(login, password);
            this.out.write(this.gson.toJson(new ResponseMessageFromServerBased(MESSAGE_FOR_CLIENT_SIGNUP_SUCCESSFUL)) + NEW_LINE);
            this.out.flush();
        } catch (RuntimeException | IOException e) {
            this.out.write(this.gson.toJson(new ResponseMessageFromServerBased(MESSAGE_ERROR_REGISTRATION_CLIENT)) + NEW_LINE);
            this.out.flush();
            throw new RuntimeException(e);
        }
    }


    private void authorization(String login, String password) throws IOException, RuntimeException {
        try {
            if (this.usersService.authorization(login, password).isPresent()) {
                this.currentUser = this.usersService.authorization(login, password).get();
                this.out.write(this.gson.toJson(new ResponseMessageFromServerBased(MESSAGE_FOR_CLIENT_SIGNIN_SUCCESSFUL)) + NEW_LINE);
                this.out.flush();
            } else {
                throw new RuntimeException(MESSAGE_ERROR_AUTHORIZATION_CLIENT);
            }
        } catch (RuntimeException | IOException e) {
            this.out.write(this.gson.toJson(new ResponseMessageFromServerBased(e.getMessage())) + NEW_LINE);
            this.out.flush();
            throw new RuntimeException(e);
        }
    }


    private void selectMenuChatroom() throws IOException, IllegalAccessException {
        label:
        while (true) {
            String clientWord = this.in.readLine();
            MessageToServerBase itemMenu = gson.fromJson(clientWord, MessageToServerBase.class);
            MessageRoomInfoToServer messageRoomInfo = gson.fromJson(clientWord, MessageRoomInfoToServer.class);
            switch (itemMenu.getItemMenu()) {
                case TEXT_CREATE_ROOM:
                    createRoom(messageRoomInfo.getRoomInfo().getName());
                    break;
                case TEXT_CHOOSE_ROOM:
                    sendListRoom();
                    selectRoom();
                    break;
                case TEXT_EXIT:
                    break label;
            }
        }
    }

    private void createRoom(String nameRoom) throws IOException, RuntimeException {
        try {
            this.chatRoomService.createRoom(nameRoom, currentUser);
            this.out.write(this.gson.toJson(new ResponseMessageFromServerBased(MESSAGE_FOR_CLIENT_CREATE_ROOM_SUCCESSFUL)) + NEW_LINE);
            this.out.flush();
        } catch (RuntimeException | IOException e) {
            System.out.println(e.getMessage());
            this.out.write(this.gson.toJson(new ResponseMessageFromServerBased(MESSAGE_ERROR_CREATE_CHATROOM)) + NEW_LINE);
            this.out.flush();
        }
    }


    private void sendListRoom() throws IOException {
        ArrayList<Chatroom> listRooms = (ArrayList<Chatroom>) chatroomRepository.findAll();
        this.out.write(this.gson.toJson(listRooms) + NEW_LINE);
        this.out.flush();
    }


    private void selectRoom() throws IOException, IllegalAccessException {
        while (true) {
            RoomSchema roomSchema = gson.fromJson(this.in.readLine(), RoomSchema.class);
            if (roomSchema.getName().equals(TEXT_EXIT)) {
                break;
            } else if (chatroomRepository.findByName(roomSchema.getName()).isPresent()) {
                this.nameChatRoom = roomSchema.getName();
                this.out.write(this.gson.toJson(new ResponseMessageFromServerBased("Вы находитесь в комнате " + roomSchema.getName() + "---")) + NEW_LINE);
                this.out.flush();
                List<Message> listRooms = messageService.getLastThirtyMessage(this.nameChatRoom);
                this.out.write(this.gson.toJson(listRooms) + NEW_LINE);
                this.out.flush();
                messaging();
                this.nameChatRoom = "";
                break;
            } else {
                this.out.write(this.gson.toJson("Ошибка: введено не корректное значение") + NEW_LINE);
                this.out.flush();
            }
        }
    }


    private void sendMessage(String msg) {
        try {
            this.out.write(this.gson.toJson(new ResponseMessageFromServerBased(msg)) + NEW_LINE);
            this.out.flush();
        } catch (IOException ignored) {
        }
    }

    private void messaging() throws IOException, IllegalAccessException {
        while (true) {
            ResponseMessageToServerBased responseMessageToServerBased = this.gson.fromJson(this.in.readLine(), ResponseMessageToServerBased.class);
            if (responseMessageToServerBased.getMessage().equals(TEXT_EXIT)) {
                this.out.write(this.gson.toJson(new ResponseMessageFromServerBased(TEXT_EXIT)) + NEW_LINE);
                this.out.flush();
                break;
            }

            Chatroom chatroom = this.chatroomRepository.findByName(this.nameChatRoom).get();
            Message message = new Message(null, this.currentUser, chatroom, responseMessageToServerBased.getMessage(), LocalDateTime.now());
            this.messageRepository.save(message);
            for (SomeClientSocket socket : Server.listSockets) {
                if (socket.nameChatRoom.equals(this.nameChatRoom)) {
                    socket.sendMessage(this.currentUser.getLogin() + ": " + responseMessageToServerBased.getMessage());
                }
            }
        }
    }

}
