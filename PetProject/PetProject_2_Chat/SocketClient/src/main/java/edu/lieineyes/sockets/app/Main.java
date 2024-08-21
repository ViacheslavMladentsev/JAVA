package edu.lieineyes.sockets.app;

import com.beust.jcommander.JCommander;
import edu.lieineyes.sockets.client.Client;
import edu.lieineyes.sockets.validator.InputArgs;

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
