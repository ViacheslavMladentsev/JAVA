package com.lieineyes.calculator.view.creditform;

import com.lieineyes.calculator.presenter.CreditFormPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class CreditFormControllerImpl implements ICreditFormController {

    private static final String PATH_INFO_CREDIT_CALCULATOR_VIEW = "/viewFXML/InformationCreditForm.fxml";

    public static final String TITLE_INFO_CREDIT_CALCULATOR = "Information about Credit SmartCalc v3.0";

    private static final String PATH_MAIN_WINDOW_VIEW = "/viewFXML/MainForm.fxml";

    public static final String TITLE_MAIN_WINDOW_APPLICATION = "Mathematical SmartCalc v3.0";

    private static final String PATH_DEPOSIT_WINDOW_VIEW = "/viewFXML/DepositForm.fxml";

    public static final String TITLE_DEPOSIT_WINDOW_APPLICATION = "Deposit SmartCalc v3.0";

    @FXML
    private AnchorPane mainPane;

    private CreditFormPresenter creditFormPresenter;


    @FXML
    void initialize() {
        this.creditFormPresenter = new CreditFormPresenter(this);
    }

    @FXML
    void onMathematicalCalculatorSelect(ActionEvent event) throws IOException {
        this.creditFormPresenter.switchScene(PATH_MAIN_WINDOW_VIEW, TITLE_MAIN_WINDOW_APPLICATION);
    }

    @FXML
    void onDepositCalculatorSelect(ActionEvent event) throws IOException {
        this.creditFormPresenter.switchScene(PATH_DEPOSIT_WINDOW_VIEW, TITLE_DEPOSIT_WINDOW_APPLICATION);
    }

    @FXML
    void onMenuHelpAboutClick(ActionEvent event) throws IOException {
        this.creditFormPresenter.openInformationForm(PATH_INFO_CREDIT_CALCULATOR_VIEW, TITLE_INFO_CREDIT_CALCULATOR);
    }

    @FXML
    void onButtonCalculateClick(ActionEvent event) {
        this.creditFormPresenter.clearView();
    }

    @FXML
    void onButtonClearClick(ActionEvent event) {

    }


    @Override
    public Stage getMainStage() {
        return (Stage) this.mainPane.getScene().getWindow();
    }
}



