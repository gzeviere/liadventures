package umons.ac.be.liadventures.application;

import javafx.stage.Stage;
import umons.ac.be.liadventures.view.Controller;

import java.io.IOException;

public class Application extends javafx.application.Application {
    Stage window;

    @Override
    public void start(Stage primaryStage) throws IOException {

        try{
            Controller controller = new Controller();
            window = controller.getWindow();
            window.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}