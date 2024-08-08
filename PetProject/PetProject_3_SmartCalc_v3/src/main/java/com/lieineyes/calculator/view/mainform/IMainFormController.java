package com.lieineyes.calculator.view.mainform;

import com.lieineyes.calculator.view.IBasedFormController;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;


public interface IMainFormController extends IBasedFormController {

    String getFieldInputExpression();

    void setFieldInputExpression(String token);

    void deleteLsatSymbolOnInputLabel();

    String getValueX();

    void setValueX(String valueX);

    ListView<String> getListViewExpression();

    ObservableList<String> getExpressionItems();
}
