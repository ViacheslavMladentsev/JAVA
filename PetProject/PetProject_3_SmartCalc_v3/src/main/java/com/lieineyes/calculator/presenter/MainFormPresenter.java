package com.lieineyes.calculator.presenter;

import com.google.gson.Gson;
import com.lieineyes.calculator.model.ModelCalculator;
import com.lieineyes.calculator.view.graphform.GraphFormControllerImpl;
import com.lieineyes.calculator.view.mainform.IMainFormController;
import com.lieineyes.calculator.view.xform.XFormControllerImpl;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;


public class MainFormPresenter extends BasedPresenter<IMainFormController> {

    private static final String PATH_JSON_HISTORY_FILE = System.getenv("HOME") + "/Desktop/SmartCalc_v_3_0/conf/history_expression.json";

    private static final String PATH_X_FORM_VIEW = "/viewFXML/XForm.fxml";

    public static final String TITLE_X_FORM = "input X";

    private static final String PATH_GRAPH_FORM_VIEW = "/viewFXML/Graph.fxml";

    public static final String TITLE_GRAPH_FORM = "Function Graph";

    private static final String NOT_ERROR = "0";

    private static final String TITLE_WINDOW_ERROR = "Ошибка";

    private static final String TEXT_HEADER_INPUT_ERROR = "Некорректное выражение";

    private static final String TEXT_WARNING = "Внимание!";

    private static final String TEXT_INCORRECT_DATA = "Некорректные данные!";

    private static final String TEXT_EXIST_FUNCTION = "Для построения графика функции необходимо ввести выражение!";

    private static final String DELIMITER_FOR_SPLIT_EXPRESSION = " = ";

    private static final String TEXT_X = "x";

    private XFormControllerImpl childrenXForm;

    private GraphFormControllerImpl childrenGraphForm;

    private final Gson gson = new Gson();


    public MainFormPresenter(IMainFormController view) {
        super(view);
    }


    public void openGraphForm(String inputExpression) throws IOException {
        if (inputExpression.contains(TEXT_X)) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_GRAPH_FORM_VIEW));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(TITLE_GRAPH_FORM);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.iMainForm.getMainStage().getScene().getWindow());
            childrenGraphForm = fxmlLoader.getController();
            childrenGraphForm.setParentController(this.iMainForm);
            stage.setResizable(false);
            stage.showAndWait();
        } else {
            showAlertWarningExistsExpression();
        }
    }

    private void showAlertWarningExistsExpression() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(TEXT_WARNING);
        alert.setHeaderText(TEXT_INCORRECT_DATA);
        alert.setContentText(TEXT_EXIST_FUNCTION);
        alert.showAndWait();
    }

    public void addTokenToInputExpression(String str) {
        this.iMainForm.setFieldInputExpression(this.iMainForm.getFieldInputExpression() + str);
    }

    public void clearLabelInputExpression() {
        this.iMainForm.setFieldInputExpression("");
    }

    public void deleteLastSymbol() {
        if (!this.iMainForm.getFieldInputExpression().isEmpty()) {
            this.iMainForm.deleteLsatSymbolOnInputLabel();
        }
    }

    public void calculatedTotalResult(String inputExpression) throws IOException {
        if (inputExpression.contains(TEXT_X)) {
            openXForm();
        }
        ModelCalculator temp = new ModelCalculator();
        String error = temp.validInputExpression(inputExpression);
        if (error.equals(NOT_ERROR)) {
            Object[] result = temp.calculate(inputExpression, this.iMainForm.getValueX());
            this.iMainForm.setFieldInputExpression(result[0].toString());
            String historyExpressionItem = inputExpression + DELIMITER_FOR_SPLIT_EXPRESSION + result[0];
            this.iMainForm.getListViewExpression().getItems().add(historyExpressionItem);
            saveHistory();
        } else {
            errorDialog(error);
        }
    }

    public void loadHistory() {
        try {
            File f = new File(PATH_JSON_HISTORY_FILE);
            if (f.exists() && !f.isDirectory()) {
                FileReader fr = new FileReader(f);
                ArrayList<String> temp = this.gson.fromJson(fr, ArrayList.class);
                if (temp != null) {
                    this.iMainForm.getExpressionItems().addAll(temp);
                    this.iMainForm.getListViewExpression().setItems(this.iMainForm.getExpressionItems());
                }
                fr.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void saveHistory() {
        try {
            FileWriter fw = new FileWriter(PATH_JSON_HISTORY_FILE);
            this.gson.toJson(this.iMainForm.getListViewExpression().getItems(), fw);
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void openXForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_X_FORM_VIEW));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(TITLE_X_FORM);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.iMainForm.getMainStage().getScene().getWindow());
        childrenXForm = fxmlLoader.getController();
        childrenXForm.setParentController(this.iMainForm);
        stage.showAndWait();
    }

    private void errorDialog(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(TITLE_WINDOW_ERROR);
        alert.setHeaderText(TEXT_HEADER_INPUT_ERROR);
        alert.setContentText(error);
        alert.showAndWait();
    }

    public void selectExpressionListView() {
        this.iMainForm.getListViewExpression().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String[] expression = newValue.split(DELIMITER_FOR_SPLIT_EXPRESSION);
                iMainForm.setFieldInputExpression(expression[0]);
            }
        });
    }

    public void clearListView() {
        this.iMainForm.getListViewExpression().getItems().clear();
        try (FileWriter writer = new FileWriter(PATH_JSON_HISTORY_FILE)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
