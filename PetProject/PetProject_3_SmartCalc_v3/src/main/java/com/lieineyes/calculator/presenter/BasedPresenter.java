package com.lieineyes.calculator.presenter;

import com.lieineyes.calculator.view.IBasedFormController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BasedPresenter<T extends IBasedFormController> {

    protected T iMainForm;

    public BasedPresenter(T IMainForm) {
        this.iMainForm = IMainForm;
    }

    public void openInformationForm(String pathFXML, String titleFXML) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pathFXML));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(titleFXML);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.iMainForm.getMainStage().getScene().getWindow());
        stage.show();
    }

    public void switchScene(String pathFXML, String titleFXML) throws IOException {
        Stage mainStage = this.iMainForm.getMainStage();
        mainStage.setTitle(titleFXML);
        mainStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(pathFXML))));
        mainStage.show();
    }
}
