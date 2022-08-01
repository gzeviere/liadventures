module umons.ac.be.liadventures {
    requires javafx.controls;
    requires javafx.fxml;


    opens umons.ac.be.liadventures to javafx.fxml;
    exports umons.ac.be.liadventures;
}