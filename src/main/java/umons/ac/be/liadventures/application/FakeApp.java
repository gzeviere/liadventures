package umons.ac.be.liadventures.application;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import umons.ac.be.liadventures.view.Controller;
import umons.ac.be.liadventures.view.MyButton;

import java.io.IOException;

public class FakeApp {
    Stage window;
    Scene scene1, scene2;
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;

        //button1
        MyButton button1 = new MyButton("play");
        button1.setOnAction(event -> {
            window.setScene(scene2);
        });

        //layout (scene) 1
        AnchorPane layout1 = new AnchorPane();
        layout1.getChildren().add(button1);
        scene1 = new Scene(layout1, 200, 200);

        //button 2
        MyButton button2 = new MyButton("changed my mind");
        button2.setOnAction(event -> window.setScene(scene1));

        //layout (scene) 2
        VBox layout2 = new VBox(20);
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2, 300, 300);

        //what to show at first
        window.setScene(scene1);
        window.setTitle("title");

        window.show();
    }
}
