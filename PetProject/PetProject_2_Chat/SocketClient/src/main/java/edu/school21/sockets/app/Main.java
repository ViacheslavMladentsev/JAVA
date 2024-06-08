package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import edu.school21.sockets.client.Client;
import edu.school21.sockets.validator.InputArgs;

import java.io.IOException;


public class Main {

    private static final String LOCALHOST = "localhost";


    public static void main(String[] args) {
        try {
            InputArgs arguments = new InputArgs();
            JCommander.newBuilder().addObject(arguments).build().parse(args);
            new Client().start(LOCALHOST, arguments.getPort());
        } catch (IOException | RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

}
