package edu.lieineyes.sockets.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


public class Server {

    private static final String MESSAGE_START_SERVER = "Сервер запущен!\n";

    private static final String MESSAGE_STOP_SERVER = "Сервер остановлен!\n";

    private static final String MESSAGE_ERROR_INIT_SERVER = "Ошибка: неудачный запуск сервера, аварийная остановка =(";


    static final LinkedList<edu.lieineyes.sockets.server.SomeClientSocket> listSockets = new LinkedList<>();


    public void start(int port) throws IOException {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println(MESSAGE_START_SERVER);
            while (true) {
                Socket clientSocket = server.accept();
                try {
                    listSockets.add(new edu.lieineyes.sockets.server.SomeClientSocket(clientSocket));
                } catch (IOException e) {
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            System.out.println(MESSAGE_ERROR_INIT_SERVER);
            throw new IOException(e);
        } finally {
            System.out.println(MESSAGE_STOP_SERVER);
        }
    }

}
