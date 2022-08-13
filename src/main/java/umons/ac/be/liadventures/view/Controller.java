package umons.ac.be.liadventures.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.Scene;
import umons.ac.be.liadventures.application.res.Difficulty;
import umons.ac.be.liadventures.application.res.Dungeon;
import umons.ac.be.liadventures.application.res.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;

public class Controller {

    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;

    //window
    private Stage window;

    //layouts
    private AnchorPane menuLayout;
    private GridPane gameLayout;
    private AnchorPane pauseLayout;
    private AnchorPane optionsLayout;

    //scenes
    private Scene mainMenu, gameScene, gameSetupScene, optionsScene, pauseMenu;

    private VBox buttonBox;
    private final ToggleGroup group = new ToggleGroup();
    private Background gameBackground;
    Background labelBackground;

    //temp
    Dungeon dungeon;
    Player lia;

    public Controller() {

        window = new Stage();

        //modify if enough time
        window.setResizable(false);

        window.setTitle("Liadventures");
        try {
            window.getIcons().add(new Image(new FileInputStream("src/main/resources/textures/menus/icon.png")));
        }catch (FileNotFoundException e){
            //no icon no problem
        }


        setBackground();
        createScenes();

        menuLayout.setBackground(gameBackground);
        window.setScene(mainMenu);
    }

    public Stage getWindow(){
        return window;
    }

    public Scene getCurrentScene(){
        return window.getScene();
    }

    private void createScenes(){
        createMainMenuScene();
        createGameSetupScene();

        //option Menu
        //Scene optionsScene = new Scene();
    }

    private void createMainMenuScene(){
        menuLayout = new AnchorPane();
        mainMenu = new Scene(menuLayout, WIDTH, HEIGHT);

        //buttons
        MyButton playButton = new MyButton("Play Full Scenario");
        MyButton lootingButton = new MyButton("Practise Looting");
        MyButton optionsButton = new MyButton("Options");
        MyButton exitButton = new MyButton("Exit");

        buttonBox = new VBox(5);

        buttonBox.setLayoutX(WIDTH/2 - 100);
        buttonBox.setLayoutY(100);

        buttonBox.getChildren().addAll(playButton, lootingButton, optionsButton, exitButton);
        menuLayout.getChildren().add(buttonBox);

        playButton.setOnAction(event -> {
            window.setScene(gameSetupScene);
        });
        //lootingButton.setOnAction(event -> );
        //optionsButton.setOnAction(event -> );
        exitButton.setOnAction(event -> window.close());


        menuLayout.setBackground(gameBackground);
    }

    private void createGameScene(){
        gameLayout = new GridPane();

        gameLayout.setPadding(new Insets(20));
        gameLayout.setHgap(25);
        gameLayout.setVgap(15);

        //statistics
        labelBackground = new Background(new BackgroundFill(Color.rgb(190,190,190,0.95), CornerRadii.EMPTY, Insets.EMPTY));

        Label ability = new Label("Ability : " + Player.getAbility());
        ability.setPadding(new Insets(5));
        ability.setBackground(labelBackground);

        Label endurance = new Label("Endurance : " + Player.getEndurance());
        endurance.setPadding(new Insets(5));
        endurance.setBackground(labelBackground);

        Label luck = new Label("Luck : " + Player.getLuck());
        luck.setPadding(new Insets(5));
        luck.setBackground(labelBackground);

        Label bagCapacity = new Label("Bag capacity : " + Player.getBagCapacity());
        bagCapacity.setPadding(new Insets(5));
        bagCapacity.setBackground(labelBackground);

        VBox statistics = new VBox(10);
        statistics.setAlignment(Pos.CENTER_LEFT);
        statistics.getChildren().addAll(ability, endurance, luck, bagCapacity);

        gameLayout.add(statistics, 0, 0);

        //Move buttons
        MyButton.MovementButton upButton = new MyButton.MovementButton("↑");
        MyButton.MovementButton leftButton = new MyButton.MovementButton("←");
        MyButton.MovementButton downButton = new MyButton.MovementButton("↓");
        MyButton.MovementButton rightButton = new MyButton.MovementButton("→");

        GridPane movementButtons = new GridPane();
        movementButtons.setPadding(new Insets(2));
        movementButtons.setHgap(2);
        movementButtons.setVgap(2);
        movementButtons.add(upButton, 1, 0);
        movementButtons.add(leftButton, 0, 1);
        movementButtons.add(downButton, 1, 1);
        movementButtons.add(rightButton, 2, 1);

        gameLayout.add(movementButtons, 0, 2);

        //text area
        TextArea textArea = new TextArea();
        gameLayout.add(textArea, 1, 2);

        //game grid pane
        dungeon.fillPane();
        GridPane gamePane = dungeon.getGamePane(); //no padding, no hgap/vgap

        gameLayout.add(gamePane,1, 0);
        gameLayout.setBackground(gameBackground);

        gameScene = new Scene(gameLayout, WIDTH, HEIGHT);
    }

    private void createGameSetupScene(){
        VBox gameSetupLayout = new VBox();

        VBox difficultySelector = new VBox();
        Label difficultySelectorLabel = new Label("Select a difficulty");
        //difficultySelectorLabel.setBackground(labelBackground);

        RadioButton easy = new RadioButton("Easy");
        easy.setToggleGroup(group);

        RadioButton normal = new RadioButton("Normal");
        normal.setToggleGroup(group);
        normal.setSelected(true);

        RadioButton hard = new RadioButton("Hard");
        hard.setToggleGroup(group);

        RadioButton extreme = new RadioButton("EXTREME");
        extreme.setToggleGroup(group);

        difficultySelector.getChildren().addAll(difficultySelectorLabel, easy,normal,hard,extreme);
        difficultySelector.setBackground(labelBackground);

        HBox sizeSelector = new HBox();
        TextField sizeField = new TextField("5");
        Label sizeLabel = new Label("Select dungeon size n x n (enter a number between 4 and 9)");
        sizeSelector.setSpacing(10);
        sizeSelector.getChildren().addAll(sizeLabel, sizeField);

        MyButton playButton2 = new MyButton("PLAY");

        gameSetupLayout.setSpacing(50);
        gameSetupLayout.getChildren().addAll(difficultySelector, sizeSelector, playButton2);
        gameSetupLayout.setAlignment(Pos.CENTER);
        gameSetupLayout.setPadding(new Insets(50));

        playButton2.setOnAction(event -> {
            RadioButton diffButton = (RadioButton) group.getSelectedToggle();
            Difficulty difficulty = Difficulty.getFromString(diffButton.getText().toUpperCase());
            int size;

            try {
                //endIndex creates problem : only the first char of sizeField will be parsed into an int, so "5875875" would be a correct input
                size = Integer.parseInt(sizeField.getCharacters(), 0, 1, 10);
            }catch (NumberFormatException e){
                size = 0;
            }
            if(size < 4 || size > 9 )
                return;

            lia = new Player();
            dungeon = new Dungeon(8, difficulty);

            createGameScene();

            window.setScene(gameScene);
        });

        gameSetupLayout.setBackground(gameBackground);

        gameSetupScene = new Scene(gameSetupLayout, WIDTH, HEIGHT);
    }

    private void createButtons(){

    }

    private void setBackground(){

        InputStream is = null;
        try {
            is = new FileInputStream("src/main/resources/textures/menus/background.jpg");
        } catch (Exception e){
            e.printStackTrace();
        }
        Image backgroundImage = new Image(is, WIDTH, HEIGHT, false, true); //require non null
        BackgroundImage bckg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        gameBackground = new Background(bckg);

    }

}