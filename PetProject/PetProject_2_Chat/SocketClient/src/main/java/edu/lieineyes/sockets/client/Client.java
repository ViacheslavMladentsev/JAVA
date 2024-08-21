package edu.lieineyes.sockets.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.lieineyes.sockets.schemasMessaging.MessageSchema;
import edu.lieineyes.sockets.schemasMessaging.RoomSchema;
import edu.lieineyes.sockets.schemasMessaging.toServer.MessageRoomInfoToServer;
import edu.lieineyes.sockets.schemasMessaging.toServer.MessageToServerBase;
import edu.lieineyes.sockets.schemasMessaging.toServer.MessageUserInfoToServer;
import edu.lieineyes.sockets.schemasMessaging.fromServer.ResponseMessageFromServerBased;
import edu.lieineyes.sockets.schemasMessaging.UserSchema;
import edu.lieineyes.sockets.schemasMessaging.toServer.ResponseMessageToServerBased;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Client {

    private static final String MESSAGE_START_CLIENT = "Клиент запущен!";

    private static final String MESSAGE_STOP_CLIENT = "Клиент остановлен!";

    private static final String MESSAGE_ERROR_INIT_CLIENT = "Ошибка: неудачный запуск клиента, аварийная остановка =(";

    private static final String MESSAGE_EMERGENCY_STOP_CLIENT = "Аварийная остановка клиента...";

    private static final String BOOT_MENU =
            "1. SignUp\n" +
                    "2. SignIn\n" +
                    "3. Exit";

    private static final String ITEM_SIGNUP = "1";

    private static final String ITEM_SIGNIN = "2";

    private static final String ITEM_EXIT_START_MENU = "3";

    private static final String TEXT_SIGN_UP = "signup";

    private static final String TEXT_SIGN_IN = "signin";

    private static final String TEXT_EXIT = "exit";

    private static final String ERROR_BOOT_MENU = "Ошибка: некорректный выбор меню!\n" +
            "Повторите попытку, и введите нужный номер пункта меню:";

    private static final String MESSAGE_INPUT_USERNAME = "Enter username:\n";

    private static final String MESSAGE_INPUT_PASSWORD = "Enter password:\n";

    private static final String MENU_CHATROOM =
            "1. Create room\n" +
                    "2. Choose room\n" +
                    "3. Exit";

    private static final String ITEM_CREATE_ROOM = "1";

    private static final String ITEM_CHOOSE_ROOM = "2";

    private static final String ITEM_EXIT_MENU_CHATROOM = "3";

    private static final String TEXT_CREATE_ROOM = "create room";

    private static final String TEXT_CHOOSE_ROOM = "choose room";

    private static final String MESSAGE_INPUT_NAMEROOM = "Enter name chatroom:\n";

    private static final String NEW_LINE = "\n";

    private static final String STRING_ERROR = "Ошибка:";

    private static final String POINT_WITH_SPACE = ". ";

    private static final String MESSAGE_ERROR_INCORRECT_ITEM_ROOM = "Ошибка: введён некорректный номер комнаты. Повторите попытку.";


    private Socket clientSocket;

    private BufferedReader consoleInput;

    private BufferedReader in;

    private BufferedWriter out;

    Gson gson = new Gson();


    public void start(String address, int port) throws IOException, RuntimeException {
        try {
            this.clientSocket = new Socket(address, port);
        } catch (IOException e) {
            System.out.println(MESSAGE_ERROR_INIT_CLIENT);
            throw new IOException(e);
        }
        System.out.println(MESSAGE_START_CLIENT);
        try {
            this.consoleInput = new BufferedReader(new InputStreamReader(System.in));
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            ResponseMessageFromServerBased responseMessageFromServerBased = this.gson.fromJson(this.in.readLine(), ResponseMessageFromServerBased.class);
            System.out.println(responseMessageFromServerBased.getMessage());
            selectStartMenu();
        } catch (IOException | RuntimeException | InterruptedException e) {
            System.out.println(MESSAGE_EMERGENCY_STOP_CLIENT);
            System.out.println(e.getMessage());
        } finally {
            this.downService();
            System.out.println(MESSAGE_STOP_CLIENT);
        }
    }

    private void downService() {
        try {
            if (!this.clientSocket.isClosed()) {
                this.clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {
        }
    }


    private void selectStartMenu() throws IOException, RuntimeException, InterruptedException {
        myLoop:
        while (true) {
            System.out.println(BOOT_MENU);
            String clientWord = this.consoleInput.readLine();
            switch (clientWord) {
                case ITEM_SIGNUP:
                    registerAuthorization(TEXT_SIGN_UP);
                    break;
                case ITEM_SIGNIN:
                    if (registerAuthorization(TEXT_SIGN_IN)) {
                        selectMenuChatroom();
                    }
                    break;
                case ITEM_EXIT_START_MENU:
                    this.out.write(gson.toJson(new MessageToServerBase(TEXT_EXIT)) + NEW_LINE);
                    this.out.flush();
                    break myLoop;
                default:
                    System.out.println(ERROR_BOOT_MENU);
                    break;
            }
        }
    }

    private boolean registerAuthorization(String itemMenu) throws IOException {
        UserSchema user = new UserSchema();
        System.out.print(MESSAGE_INPUT_USERNAME);
        user.setLogin(consoleInput.readLine());
        System.out.print(MESSAGE_INPUT_PASSWORD);
        user.setPassword(consoleInput.readLine());
        this.out.write(gson.toJson(new MessageUserInfoToServer(itemMenu, user)) + NEW_LINE);
        this.out.flush();
        ResponseMessageFromServerBased responseMessageFromServerBased = this.gson.fromJson(this.in.readLine(), ResponseMessageFromServerBased.class);
        System.out.println(responseMessageFromServerBased.getMessage());
        return !responseMessageFromServerBased.getMessage().startsWith(STRING_ERROR);
    }


    private void selectMenuChatroom() throws IOException, InterruptedException {
        myLoop:
        while (true) {
            System.out.println(MENU_CHATROOM);
            String clientWord = this.consoleInput.readLine();
            switch (clientWord) {
                case ITEM_CREATE_ROOM:
                    createRoom();
                    break;
                case ITEM_CHOOSE_ROOM:
                    chooseRoom();
                    break;
                case ITEM_EXIT_MENU_CHATROOM:
                    this.out.write(gson.toJson(new MessageToServerBase(TEXT_EXIT)) + NEW_LINE);
                    this.out.flush();
                    break myLoop;
                default:
                    System.out.println(ERROR_BOOT_MENU);
                    break;
            }
        }
    }


    private void createRoom() throws IOException {
        RoomSchema roomSchema = new RoomSchema();
        System.out.print(MESSAGE_INPUT_NAMEROOM);
        roomSchema.setName(consoleInput.readLine());
        this.out.write(gson.toJson(new MessageRoomInfoToServer(TEXT_CREATE_ROOM, roomSchema)) + NEW_LINE);
        this.out.flush();
        ResponseMessageFromServerBased responseMessage = this.gson.fromJson(this.in.readLine(), ResponseMessageFromServerBased.class);
        System.out.println(responseMessage.getMessage());
    }


    private void chooseRoom() throws IOException, InterruptedException {
        this.out.write(gson.toJson(new MessageToServerBase(TEXT_CHOOSE_ROOM)) + NEW_LINE);
        this.out.flush();

        ArrayList<RoomSchema> listRoomSchema = this.gson.fromJson(this.in.readLine(),
                new TypeToken<ArrayList<RoomSchema>>() {
                }.getType());

        int countRowItemMenu = 1;
        for (RoomSchema roomSchema : listRoomSchema) {
            System.out.println(countRowItemMenu + POINT_WITH_SPACE + roomSchema.getName());
            ++countRowItemMenu;
        }
        System.out.println(countRowItemMenu + POINT_WITH_SPACE + TEXT_EXIT);
        selectRoom(listRoomSchema);
    }


    private void selectRoom(ArrayList<RoomSchema> listRoomSchema) throws IOException, InterruptedException {
        while (true) {
            int indexRoom = Integer.parseInt(consoleInput.readLine());
            RoomSchema roomSchema = new RoomSchema();
            if (indexRoom == listRoomSchema.size() + 1) {
                roomSchema.setName(TEXT_EXIT);
                this.out.write(gson.toJson(roomSchema) + NEW_LINE);
                this.out.flush();
                break;
            } else if (indexRoom <= listRoomSchema.size() && indexRoom > 0) {
                roomSchema.setName(listRoomSchema.get(indexRoom - 1).getName());
                this.out.write(gson.toJson(roomSchema) + NEW_LINE);
                this.out.flush();

                ResponseMessageFromServerBased responseMessage = this.gson.fromJson(this.in.readLine(), ResponseMessageFromServerBased.class);
                System.out.println(responseMessage.getMessage());

                List<MessageSchema> listLastThirtyRoom = this.gson.fromJson(this.in.readLine(),
                        new TypeToken<List<MessageSchema>>() {
                        }.getType());

                for (MessageSchema messageSchema : listLastThirtyRoom) {
                    System.out.println(messageSchema.getAuthor().getLogin() + ": " + messageSchema.getMessage());
                }

                Thread thread_1 = new ReadMsg();
                thread_1.start();
                Thread thread_2 = new WriteMsg();
                thread_2.start();

                thread_1.join();
                thread_2.join();
            } else {
                System.out.println(MESSAGE_ERROR_INCORRECT_ITEM_ROOM);
                continue;
            }
            break;
        }
    }


    private class ReadMsg extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    ResponseMessageFromServerBased responseMessage = Client.this.gson.fromJson(Client.this.in.readLine(), ResponseMessageFromServerBased.class);
                    if (responseMessage.getMessage().equals(TEXT_EXIT)) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    System.out.println(responseMessage.getMessage());
                }
            } catch (IOException e) {
                Client.this.downService();
            }
        }

    }

    public class WriteMsg extends Thread {

        @Override
        public void run() {
            while (true) {
                String clientWord;
                try {
                    clientWord = Client.this.consoleInput.readLine();
                    if (clientWord.equals(TEXT_EXIT)) {
                        Client.this.out.write(Client.this.gson.toJson(new ResponseMessageToServerBased(TEXT_EXIT)) + NEW_LINE);
                        Client.this.out.flush();
                        Thread.currentThread().interrupt();
                        break;
                    } else {
                        Client.this.out.write(Client.this.gson.toJson(new ResponseMessageToServerBased(clientWord)) + NEW_LINE);
                        Client.this.out.flush();
                    }
                } catch (IOException e) {
                    Client.this.downService();
                }
            }
        }

    }

}
