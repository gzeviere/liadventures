package umons.ac.be.liadventures.application;

import javafx.stage.Stage;
import umons.ac.be.liadventures.view.Controller;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) {

        try{
            Controller controller = Controller.getInstance();
            Stage window = controller.getWindow();
            window.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}