package com.lieineyes.calculator.presenter;

import com.lieineyes.calculator.model.ModelCalculator;
import com.lieineyes.calculator.view.graphform.IGraphFormController;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;


public class GraphFormPresenter {

    private static final String TEXT_WARNING = "Внимание!";

    private static final String TEXT_INCORRECT_DATA = "Некорректные данные";

    private static final String TEXT_INCORRECT_INPUT_AREAS_DISTRIBUTION = "Максимальное значение области определения " +
            "графика функции по оси %s не может быть меньше минимального значения той же оси";

    private static final int ITER_FOR_STEP = 10000;

    private final IGraphFormController iGraphFormController;


    public GraphFormPresenter(IGraphFormController iGraphFormController) {
        this.iGraphFormController = iGraphFormController;
    }

    public void drawGraph() {
        changedScale();
        calculateCoordinatePointGraph();
    }

    private void changedScale() {
        if (iGraphFormController.getMinX() < iGraphFormController.getMaxX()) {
            iGraphFormController.setMinX(iGraphFormController.getMinX());
            iGraphFormController.setMaxX(iGraphFormController.getMaxX());
        } else {
            showAlertWarningAreasDistribution(iGraphFormController.getXAxis());
        }
        if (iGraphFormController.getMinY() < iGraphFormController.getMaxY()) {
            iGraphFormController.setMinY(iGraphFormController.getMinY());
            iGraphFormController.setMaxY(iGraphFormController.getMaxY());
        } else {
            showAlertWarningAreasDistribution(iGraphFormController.getYAxis());
        }
    }

    private void showAlertWarningAreasDistribution(NumberAxis axis) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(TEXT_WARNING);
        alert.setHeaderText(TEXT_INCORRECT_DATA);
        alert.setContentText(String.format(TEXT_INCORRECT_INPUT_AREAS_DISTRIBUTION, axis.getLabel()));
        alert.showAndWait();
    }

    private void calculateCoordinatePointGraph() {
        ModelCalculator temp = new ModelCalculator();
        XYChart.Series<Double, Double> seriesCoordinate = new XYChart.Series<>();

        double step = (iGraphFormController.getMaxX() - iGraphFormController.getMinX()) / ITER_FOR_STEP;
        double valueX = iGraphFormController.getMinX();

        for (int count = 0; count < ITER_FOR_STEP; ++count, valueX += step) {
            Object[] result = temp.calculate(this.iGraphFormController.getParentController().getFieldInputExpression(), String.valueOf(valueX));
            double coordinateY = (double) result[0];
            if (coordinateY <= iGraphFormController.getYAxis().getUpperBound()) {
                seriesCoordinate.getData().add(new XYChart.Data<>(valueX, coordinateY));
            }
        }
        iGraphFormController.getLineChart().getData().add(seriesCoordinate);
    }

}
