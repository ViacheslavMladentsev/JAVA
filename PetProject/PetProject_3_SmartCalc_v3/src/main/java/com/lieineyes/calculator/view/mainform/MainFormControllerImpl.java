package com.lieineyes.calculator.view.mainform;

import java.io.*;
import java.util.Collections;

import com.lieineyes.calculator.presenter.MainFormPresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MainFormControllerImpl implements IMainFormController {

    private static final String PATH_INFO_MATHEMATICAL_CALCULATOR_VIEW = "/viewFXML/InformationMainForm.fxml";

    public static final String TITLE_INFO_MATHEMATICAL_CALCULATOR = "Information about Mathematical SmartCalc v3.0";

    private static final String PATH_CREDIT_WINDOW_VIEW = "/viewFXML/CreditForm.fxml";

    public static final String TITLE_CREDIT_WINDOW_APPLICATION = "Credit SmartCalc v3.0";

    private static final String PATH_DEPOSIT_WINDOW_VIEW = "/viewFXML/DepositForm.fxml";

    public static final String TITLE_DEPOSIT_WINDOW_APPLICATION = "Deposit SmartCalc v3.0";

    public static final int MAX_CHARS = 255;

    private MainFormPresenter mainFormPresenter;

    private String valueX = "0";

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextArea fieldInputExpression;

    @FXML
    private ListView<String> listViewExpression;

    ObservableList<String> expressionItems;


    @FXML
    void initialize() {
        this.mainFormPresenter = new MainFormPresenter(this);
        this.expressionItems = FXCollections.observableArrayList(Collections.emptyList());
        this.fieldInputExpression.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));
        this.mainFormPresenter.loadHistory();
    }


    @FXML
    void onClearHistorySelect(ActionEvent event) {
        this.mainFormPresenter.clearListView();
    }

    @FXML
    public void onMenuHelpAboutClick(ActionEvent event) throws IOException {
        this.mainFormPresenter.openInformationForm(PATH_INFO_MATHEMATICAL_CALCULATOR_VIEW, TITLE_INFO_MATHEMATICAL_CALCULATOR);
    }

    @FXML
    void onCreditCalculatorSelect(ActionEvent event) throws IOException {
//        saveHistory();
        this.mainFormPresenter.switchScene(PATH_CREDIT_WINDOW_VIEW, TITLE_CREDIT_WINDOW_APPLICATION);
    }

    @FXML
    void onDepositCalculatorSelect(ActionEvent event) throws IOException {
        this.mainFormPresenter.switchScene(PATH_DEPOSIT_WINDOW_VIEW, TITLE_DEPOSIT_WINDOW_APPLICATION);
    }

    @FXML
    public void onInputButtonClick(ActionEvent event) {
        this.mainFormPresenter.addTokenToInputExpression(((Button) event.getSource()).getText());
    }

    @FXML
    void onButtonACClick(ActionEvent event) {
        this.mainFormPresenter.clearLabelInputExpression();
    }


    @FXML
    void onBackspaceClick(ActionEvent event) {
        this.mainFormPresenter.deleteLastSymbol();
    }

    @FXML
    void onButtonTotalClick(ActionEvent event) throws IOException {
        this.mainFormPresenter.calculatedTotalResult(this.fieldInputExpression.getText());
    }

    @FXML
    void onButtonGraphClick(ActionEvent event) throws IOException {
        this.mainFormPresenter.openGraphForm(this.fieldInputExpression.getText());
    }

    @FXML
    void onMouseSelectItemClicked(MouseEvent event) {
        this.mainFormPresenter.selectExpressionListView();
    }


    @Override
    public String getFieldInputExpression() {
        return this.fieldInputExpression.getText();
    }

    @Override
    public void setFieldInputExpression(String token) {
        this.fieldInputExpression.setText(token);
    }

    @Override
    public void deleteLsatSymbolOnInputLabel() {
        String str = this.fieldInputExpression.getText();
        this.fieldInputExpression.setText(str.substring(0, str.length() - 1));
    }

    @Override
    public Stage getMainStage() {
        return (Stage) this.mainPane.getScene().getWindow();
    }

    @Override
    public String getValueX() {
        return this.valueX;
    }

    @Override
    public void setValueX(String valueX) {
        this.valueX = valueX;
    }

    @Override
    public ListView<String> getListViewExpression() {
        return this.listViewExpression;
    }

    @Override
    public ObservableList<String> getExpressionItems() {
        return this.expressionItems;
    }

}
