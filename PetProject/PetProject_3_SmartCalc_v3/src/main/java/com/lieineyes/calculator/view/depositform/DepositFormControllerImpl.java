package com.lieineyes.calculator.view.depositform;

import com.lieineyes.calculator.presenter.DepositFormPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DepositFormControllerImpl implements IDepositFormController {

    private static final String PATH_INFO_DEPOSIT_CALCULATOR_VIEW = "/viewFXML/InformationDepositForm.fxml";

    public static final String TITLE_INFO_DEPOSIT_CALCULATOR = "Information about Deposit SmartCalc v3.0";

    private static final String PATH_MAIN_WINDOW_VIEW = "/viewFXML/MainForm.fxml";

    public static final String TITLE_MAIN_WINDOW_APPLICATION = "Mathematical SmartCalc v3.0";

    private static final String PATH_CREDIT_WINDOW_VIEW = "/viewFXML/CreditForm.fxml";

    public static final String TITLE_CREDIT_WINDOW_APPLICATION = "Credit SmartCalc v3.0";

    @FXML
    private AnchorPane mainPane;

    private DepositFormPresenter depositFormPresenter;


    @FXML
    void initialize() {
        this.depositFormPresenter = new DepositFormPresenter(this);
    }

    @FXML
    void onMathematicalCalculatorSelect(ActionEvent event) throws IOException {
        depositFormPresenter.switchScene(PATH_MAIN_WINDOW_VIEW, TITLE_MAIN_WINDOW_APPLICATION);
    }

    @FXML
    void onCreditCalculatorSelect(ActionEvent event) throws IOException {
        depositFormPresenter.switchScene(PATH_CREDIT_WINDOW_VIEW, TITLE_CREDIT_WINDOW_APPLICATION);
    }

    @FXML
    void onMenuHelpAboutClick(ActionEvent event) throws IOException {
        depositFormPresenter.openInformationForm(PATH_INFO_DEPOSIT_CALCULATOR_VIEW, TITLE_INFO_DEPOSIT_CALCULATOR);
    }


    @Override
    public Stage getMainStage() {
        return (Stage) this.mainPane.getScene().getWindow();
    }
}
