package umons.ac.be.liadventures.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import umons.ac.be.liadventures.application.res.Difficulty;
import umons.ac.be.liadventures.application.res.Direction;
import umons.ac.be.liadventures.application.res.Dungeon;
import umons.ac.be.liadventures.application.res.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Controller {
    private static Controller singletonController;

    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;

    //window
    private final Stage window;

    //layouts
    private AnchorPane menuLayout;
    private AnchorPane pauseMenuLayout;
    private GridPane gameLayout;
    private AnchorPane optionsLayout;

    //scenes
    private Scene mainMenu, gameScene, gameSetupScene, lootingScene, optionsScene, pauseMenu;

    private final ToggleGroup group = new ToggleGroup();
    private Background gameBackground;
    Background labelBackground;

    //temp
    private Dungeon dungeon;
    private Player lia;

    public static Controller getInstance(){
        if(singletonController == null)
            singletonController = new Controller();

        return singletonController;
    }

    private Controller() {

        window = new Stage();

        //modify if enough time
        window.setResizable(true);

        window.setTitle("Liadventures");
        try {
            window.getIcons().add(new Image(new FileInputStream("src/main/resources/textures/menus/icon.png")));
        } catch (FileNotFoundException e) {
            //no icon no problem
        }

        setBackground();
        createMainMenuScene();
        setupKeyListeners();

        menuLayout.setBackground(gameBackground);
        window.setScene(mainMenu);
    }

    public Stage getWindow() {
        return window;
    }

    public Scene getCurrentScene() {
        return window.getScene();
    }

    private void setupKeyListeners() {

    }

    private void createMainMenuScene() {
        menuLayout = new AnchorPane();

        //buttons
        MyButton playButton = new MyButton("Play Full Scenario");
        MyButton lootingButton = new MyButton("Practise Looting");
        MyButton optionsButton = new MyButton("Options");
        MyButton exitButton = new MyButton("Exit");

        VBox buttonBox = new VBox(5);

        buttonBox.setLayoutX(WIDTH / 2.0 - 100);
        buttonBox.setLayoutY(100);

        buttonBox.getChildren().addAll(playButton, lootingButton, optionsButton, exitButton);
        menuLayout.getChildren().add(buttonBox);

        playButton.setOnAction(event -> {
            createGameSetupScene();
            window.setScene(gameSetupScene);
        });
        //lootingButton.setOnAction(event -> );
        //optionsButton.setOnAction(event -> );
        exitButton.setOnAction(event -> window.close());

        mainMenu = new Scene(menuLayout, WIDTH, HEIGHT);

        menuLayout.setBackground(gameBackground);
    }

    private void createPauseMenuScene() {
        pauseMenuLayout = new AnchorPane();

        //buttons
        MyButton resumeButton = new MyButton("Resume");
        MyButton optionsButton = new MyButton("Options");
        MyButton exitButton = new MyButton("Leave Game without saving");
        MyButton quitGame = new MyButton("Exit to desktop");
        Label pauseLabel = new Label("GAME PAUSED");
        pauseLabel.setBackground(labelBackground);
        pauseLabel.setFont(Font.font(25));

        VBox buttonBox = new VBox(5);

        buttonBox.setLayoutX(WIDTH / 2.0 - 100);
        buttonBox.setLayoutY(100);

        buttonBox.getChildren().addAll(pauseLabel, resumeButton, optionsButton, exitButton, quitGame);
        pauseMenuLayout.getChildren().add(buttonBox);

        resumeButton.setOnAction(event -> window.setScene(gameScene));
        //optionsButton.setOnAction(event -> );
        exitButton.setOnAction(event -> window.setScene(mainMenu));
        quitGame.setOnAction(event -> window.close());

        pauseMenu = new Scene(pauseMenuLayout, WIDTH, HEIGHT);

        pauseMenu.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE)
                window.setScene(gameScene);
        });

        pauseMenuLayout.setBackground(gameBackground);
    }

    private void createGameSetupScene() {
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

        difficultySelector.getChildren().addAll(difficultySelectorLabel, easy, normal, hard, extreme);
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
            } catch (NumberFormatException e) {
                size = 0;
            }
            if (size < 4 || size > 9)
                return;

            dungeon = new Dungeon(size, difficulty);
            lia = dungeon.getLia();

            createGameScene();
            createPauseMenuScene();

            window.setScene(gameScene);
        });

        gameSetupLayout.setBackground(gameBackground);

        gameSetupScene = new Scene(gameSetupLayout, WIDTH, HEIGHT);

        gameSetupScene.setOnKeyPressed(event -> window.setScene(mainMenu));
    }

    private void createGameScene() {
        gameLayout = new GridPane();

        gameLayout.setPadding(new Insets(20));
        gameLayout.setHgap(25);
        gameLayout.setVgap(15);

        //statistics
        labelBackground = new Background(new BackgroundFill(Color.rgb(190, 190, 190, 0.95), CornerRadii.EMPTY, Insets.EMPTY));


        //TODO make Player non static !!!!!!!
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

        gameLayout.add(gamePane, 1, 0);
        gameLayout.setBackground(gameBackground);

        gameScene = new Scene(gameLayout, WIDTH, HEIGHT);

        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case ESCAPE :
                    window.setScene(pauseMenu);
                    break;
                case P:
                    dungeon.revealAll();
                    break;
                case Z:
                    System.out.println("z");
                    if(dungeon.move(Direction.UP)){

                    }
                    break;
                case Q:
                    System.out.println("q");
                    if(dungeon.move(Direction.LEFT)){

                    }
                    break;
                case S:
                    if(dungeon.move(Direction.DOWN)){

                    }
                    break;
                case D:
                    if(dungeon.move(Direction.RIGHT)){

                    }
                    break;
            }

        });
        dungeon.startGame();
    }

    private void setBackground() {

        InputStream is = null;
        try {
            is = new FileInputStream("src/main/resources/textures/menus/background.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert is != null;
        Image backgroundImage = new Image(is, WIDTH, HEIGHT, false, true); //require non null
        gameBackground = new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null));

    }

}