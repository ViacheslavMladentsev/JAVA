package com.lieineyes.calculator.view.graphform;

import com.lieineyes.calculator.presenter.GraphFormPresenter;
import com.lieineyes.calculator.view.mainform.IMainFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class GraphFormControllerImpl implements IGraphFormController {

    private static final String TEXT_WARNING = "Внимание!";

    private static final String TEXT_INCORRECT_DATA = "Некорректные данные";

    private static final String TEXT_INCORRECT_INPUT_NUMERIC = "Введенные данные в поле %s не являются вещественным числом";

    private IMainFormController controllerMainForm;

    GraphFormPresenter graphFormPresenter;


    @FXML
    private LineChart<Double, Double> functionGraph;

    @FXML
    private TextField textFieldXMax;

    @FXML
    private TextField textFieldXMin;

    @FXML
    private TextField textFieldYMax;

    @FXML
    private TextField textFieldYMin;

    @FXML
    private Label labelXMax;

    @FXML
    private Label labelXMin;

    @FXML
    private Label labelYMax;

    @FXML
    private Label labelYMin;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    void onButtonDisplayGraphClick(ActionEvent event) {
        graphFormPresenter.drawGraph();

    }


    @FXML
    void initialize() {
        this.graphFormPresenter = new GraphFormPresenter(this);
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
    public LineChart<Double, Double> getLineChart() {
        return functionGraph;
    }

    @Override
    public NumberAxis getXAxis() {
        return this.xAxis;
    }

    @Override
    public NumberAxis getYAxis() {
        return this.yAxis;
    }

    @Override
    public double getMinX() {
        if (isNumeric(this.textFieldXMin.getText())) {
            return Double.parseDouble(this.textFieldXMin.getText());
        } else {
            showAlertWarningNumericError(this.labelXMin.getText());
            return xAxis.getLowerBound();
        }
    }

    @Override
    public double getMaxX() {
        if (isNumeric(this.textFieldXMax.getText())) {
            return Double.parseDouble(this.textFieldXMax.getText());
        } else {
            showAlertWarningNumericError(this.labelXMax.getText());
            return xAxis.getUpperBound();
        }
    }

    @Override
    public double getMinY() {
        if (isNumeric(this.textFieldYMin.getText())) {
            return Double.parseDouble(this.textFieldYMin.getText());
        } else {
            showAlertWarningNumericError(this.labelYMin.getText());
            return yAxis.getLowerBound();
        }
    }

    @Override
    public double getMaxY() {
        if (isNumeric(this.textFieldYMax.getText())) {
            return Double.parseDouble(this.textFieldYMax.getText());
        } else {
            showAlertWarningNumericError(this.labelYMax.getText());
            return yAxis.getUpperBound();
        }
    }

    @Override
    public void setMinX(double minX) {
        this.xAxis.setLowerBound(minX);
    }

    @Override
    public void setMaxX(double maxX) {
        this.xAxis.setUpperBound(maxX);
    }

    @Override
    public void setMinY(double minY) {
        this.yAxis.setLowerBound(minY);
    }

    @Override
    public void setMaxY(double maxY) {
        this.yAxis.setUpperBound(maxY);
    }

    private void showAlertWarningNumericError(String nameField) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(TEXT_WARNING);
        alert.setHeaderText(TEXT_INCORRECT_DATA);
        alert.setContentText(String.format(TEXT_INCORRECT_INPUT_NUMERIC, nameField));
        alert.showAndWait();
    }

    private static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    @Override
    public Stage getMainStage() {
        return null;
    }
}