package umons.ac.be.liadventures.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Controller {

    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    private AnchorPane root;
    private Scene mainScene;
    private Stage mainStage;

    private final double buttonLayoutX = WIDTH/2 - 100;
    private final double buttonLayoutY = 100;

    SubScene gameScene;
    SubScene optionsScene;

    private SubScene currentScene;

    private void showSubScene(SubScene toShow){
        if(currentScene != null){ //hide current scene
        }
    }

    private void keyListener(){
        //setOn KeyPressed (listen to escape ton leave subscene
    }

    public Controller() {
        root = new AnchorPane();
        mainScene = new Scene(root, WIDTH, HEIGHT); //root
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        

        setBackground();

       createButtons();
       createSubScenes();
    }

    public Stage getMainStage(){
        return mainStage;
    }

    private void createButtons(){
        Button playButton = new Button("Play Full Scenario");
        Button lootingButton = new Button("Practise Looting");
        Button optionsButton = new Button("Options");
        Button exitButton = new Button("Exit");
        MuteButton muteButton = new MuteButton();

        root.getChildren().addAll(playButton, lootingButton, optionsButton, exitButton, muteButton);

        playButton.setLayoutX(buttonLayoutX);
        playButton.setLayoutY(buttonLayoutY);

        lootingButton.setLayoutX(buttonLayoutX);
        lootingButton.setLayoutY(buttonLayoutY + 55);

        optionsButton.setLayoutX(buttonLayoutX);
        optionsButton.setLayoutY(buttonLayoutY + 110);

        exitButton.setLayoutX(buttonLayoutX);
        exitButton.setLayoutY(buttonLayoutY + 165);

        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        lootingButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // lancer phase pillage
            }
        });
        optionsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().add(optionsScene);
            }
        });
        exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });
    }
    private void createSubScenes(){
        SubScene gameScene = new SubScene();
        SubScene optionsScene = new SubScene();


    }

    private void setBackground(){
        //InputStream stream = new FileInputStream("D:\images\elephant.jpg");
        InputStream is = null;
        try {
            is = new FileInputStream("src/main/resources/textures/menus/background.jpg");
        } catch (Exception e){
            e.printStackTrace();
        }
        Image backgroundImage = new Image(is);
        //Image backgroundImage = new Image(Objects.requireNonNull(Image.class.getResourcesAsStream("")));
        BackgroundImage bckg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        root.setBackground(new Background(bckg));
    }

}