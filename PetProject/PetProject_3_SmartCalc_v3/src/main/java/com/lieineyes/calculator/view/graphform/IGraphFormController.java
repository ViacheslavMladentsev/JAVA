package com.lieineyes.calculator.view.graphform;

import com.lieineyes.calculator.view.IBasedFormController;
import com.lieineyes.calculator.view.mainform.IMainFormController;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;


public interface IGraphFormController extends IBasedFormController {

    void setParentController(IMainFormController controllerMainForm);

    IMainFormController getParentController();

    LineChart<Double, Double> getLineChart();

    NumberAxis getXAxis();
    NumberAxis getYAxis();

    double getMinX();
    double getMaxX();
    double getMinY();
    double getMaxY();

    void setMinX(double minX);
    void setMaxX(double maxX);
    void setMinY(double minY);
    void setMaxY(double maxY);

}
