package com.lieineyes.calculator.view.xform;

import com.lieineyes.calculator.presenter.XFormPresenter;
import com.lieineyes.calculator.view.mainform.IMainFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class XFormControllerImpl implements IXFormController {

    private IMainFormController controllerMainForm;

    private XFormPresenter xFormPresenter;

    @FXML
    private TextField fieldInputX;

    @FXML
    private AnchorPane mainPane;


    @FXML
    void initialize() {
        this.xFormPresenter = new XFormPresenter(this);
    }

    @FXML
    void onButtonConfirmClick(ActionEvent event) {
        this.xFormPresenter.checkAndSaveValueX();
    }

    @Override
    public void setParentController(IMainFormController controllerMainForm) {
        this.controllerMainForm = controllerMainForm;
    }

    @Override
    public IMainFormController getParentController() {
        return this.controllerMainForm;
    }

    @Override
    public String getFieldInputX() {
        return fieldInputX.getText();
    }

    @Override
    public Stage getMainPane() {
        return (Stage) mainPane.getScene().getWindow();
    }

    @Override
    public Stage getMainStage() {
        return null;
    }
}
