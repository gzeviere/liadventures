module umons.ac.be.liadventures {
    requires javafx.controls;
    requires javafx.fxml;


    exports umons.ac.be.liadventures.application;
    opens umons.ac.be.liadventures.application to javafx.fxml;
    exports umons.ac.be.liadventures.view;
    opens umons.ac.be.liadventures.view to javafx.fxml;
    exports umons.ac.be.liadventures.application.res;
    opens umons.ac.be.liadventures.application.res to javafx.fxml;
}