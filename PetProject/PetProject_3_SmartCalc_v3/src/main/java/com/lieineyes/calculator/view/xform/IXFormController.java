package com.lieineyes.calculator.view.xform;

import com.lieineyes.calculator.view.IBasedFormController;
import com.lieineyes.calculator.view.mainform.IMainFormController;
import javafx.stage.Stage;


public interface IXFormController extends IBasedFormController {

    void setParentController(IMainFormController controllerMainForm);

    IMainFormController getParentController();

    String getFieldInputX();

    Stage getMainPane();

}
