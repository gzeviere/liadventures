package umons.ac.be.liadventures.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import umons.ac.be.liadventures.view.Controller;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        try{
            Controller controller = new Controller();
            stage = controller.getMainStage();
            stage.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}