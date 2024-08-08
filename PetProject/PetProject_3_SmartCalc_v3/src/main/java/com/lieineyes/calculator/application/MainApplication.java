package com.lieineyes.calculator.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;


public class MainApplication extends Application {

    private static final String PATH_MAIN_WINDOW_VIEW = "/viewFXML/MainForm.fxml";

    public static final String TITLE_MAIN_WINDOW_APPLICATION = "Mathematical SmartCalc v3.0";


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_MAIN_WINDOW_VIEW));
        Parent parent = fxmlLoader.load();
        Scene mainScene = new Scene(parent);
        stage.setTitle(TITLE_MAIN_WINDOW_APPLICATION);
        stage.setResizable(false);
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
