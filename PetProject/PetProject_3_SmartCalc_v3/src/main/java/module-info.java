module com.lieineyes.calculator.smartcalc_v3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.google.gson;
    requires com.dlsc.formsfx;

    exports com.lieineyes.calculator.application;
    opens com.lieineyes.calculator.application to javafx.fxml;

    exports com.lieineyes.calculator.view.mainform;
    opens com.lieineyes.calculator.view.mainform to javafx.fxml;

    exports com.lieineyes.calculator.view.creditform;
    opens com.lieineyes.calculator.view.creditform to javafx.fxml;

    exports com.lieineyes.calculator.view.depositform;
    opens com.lieineyes.calculator.view.depositform to javafx.fxml;

    exports com.lieineyes.calculator.view.xform;
    opens com.lieineyes.calculator.view.xform to javafx.fxml;

    exports com.lieineyes.calculator.view.graphform;
    opens com.lieineyes.calculator.view.graphform to javafx.fxml;

}