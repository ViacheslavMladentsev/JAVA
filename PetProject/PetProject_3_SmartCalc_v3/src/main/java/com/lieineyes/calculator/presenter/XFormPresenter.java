package com.lieineyes.calculator.presenter;

import com.lieineyes.calculator.view.xform.IXFormController;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class XFormPresenter {

    private final IXFormController IMainForm;

    public XFormPresenter(IXFormController view) {
        this.IMainForm = view;
    }

    public void checkAndSaveValueX() {
        if (isNumeric(this.IMainForm.getFieldInputX())) {
            this.IMainForm.getParentController().setValueX(this.IMainForm.getFieldInputX());
            Stage xFormStage = this.IMainForm.getMainPane();
            xFormStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Look, a Warning Dialog");
            alert.setContentText("Введенные данные не является вещественным числом");
            alert.showAndWait();
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
