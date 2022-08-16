package umons.ac.be.liadventures.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import umons.ac.be.liadventures.application.res.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;


/**
 * Singleton class used to control the main stage (window) and create its different scenes.
 * Made singleton class to be able to use the controller and its functions from anywhere in the project.
 */
public class Controller {
    private static Controller singletonController;

    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;

    //window
    private final Stage window;

    //layouts
    private AnchorPane menuLayout;
    private GridPane gameLayout;
    private GridPane gamePane;
    //private AnchorPane optionsLayout; cancelled

    //scenes
    private Scene mainMenu, gameScene, gameSetupScene, pauseMenu;
    //private Scene optionsScene; cancelled

    private final ToggleGroup difficultyGroup = new ToggleGroup();
    private Background gameBackground;
    private final Background labelBackground = new Background(new BackgroundFill(Color.rgb(190, 190, 190, 0.95), CornerRadii.EMPTY, Insets.EMPTY));

    //labels
    private Label ability, endurance, luck, bagCapacity;
    private GridPane movementButtons;

    /**
     * The logs area acts like a console, important information for the game will be displayed here
     */
    private TextArea logsArea;

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
        window.setResizable(false);
        window.setTitle("Liadventures");

        setAppIcon();
        setBackground();
        createMainMenuScene();

        window.setScene(mainMenu);
    }

    public Stage getWindow() {
        return window;
    }

    private void setAppIcon(){
        try {
            window.getIcons().add(new Image(new FileInputStream("src/main/resources/textures/menus/icon.png")));
        } catch (FileNotFoundException e) {
            //no icon no problem
        }
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
        lootingButton.setOnAction(event -> {
            lia = new Player();
            dungeon = new Dungeon(4, Difficulty.NORMAL);
            createGameScene();
            dungeon.looting();
            window.setScene(gameScene);
        });
        //optionsButton.setOnAction(event -> );
        exitButton.setOnAction(event -> window.close());

        mainMenu = new Scene(menuLayout, WIDTH, HEIGHT);

        menuLayout.setBackground(gameBackground);
    }

    private void createPauseMenuScene() {
        AnchorPane pauseMenuLayout = new AnchorPane();

        //buttons
        MyButton resumeButton = new MyButton("Resume");
        //MyButton optionsButton = new MyButton("Options");
        MyButton exitButton = new MyButton("Leave Game without saving");
        MyButton quitGame = new MyButton("Exit to desktop");
        Label pauseLabel = new Label("GAME PAUSED");
        pauseLabel.setBackground(labelBackground);
        pauseLabel.setFont(Font.font(25));

        VBox buttonBox = new VBox(5);

        buttonBox.setLayoutX(WIDTH / 2.0 - 100);
        buttonBox.setLayoutY(100);

        buttonBox.getChildren().addAll(pauseLabel, resumeButton, exitButton, quitGame);
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
        difficultySelectorLabel.setFont(Font.font(20));
        difficultySelectorLabel.setBackground(labelBackground);


        RadioButton easy = new RadioButton("Easy");
        easy.setToggleGroup(difficultyGroup);
        easy.setBackground(labelBackground);

        RadioButton normal = new RadioButton("Normal");
        normal.setToggleGroup(difficultyGroup);
        normal.setBackground(labelBackground);
        normal.setSelected(true);

        RadioButton hard = new RadioButton("Hard");
        hard.setToggleGroup(difficultyGroup);
        hard.setBackground(labelBackground);

        RadioButton extreme = new RadioButton("EXTREME");
        extreme.setToggleGroup(difficultyGroup);
        extreme.setBackground(labelBackground);

        difficultySelector.getChildren().addAll(difficultySelectorLabel, easy, normal, hard, extreme);

        HBox sizeSelector = new HBox();
        TextField sizeField = new TextField("5");
        Label sizeLabel = new Label("Select dungeon size n x n (enter a number between 4 and 9)");
        sizeLabel.setBackground(labelBackground);
        sizeLabel.setFont(Font.font(13));
        sizeSelector.setSpacing(10);
        sizeSelector.getChildren().addAll(sizeLabel, sizeField);

        MyButton playButton2 = new MyButton("PLAY");

        gameSetupLayout.setSpacing(50);
        gameSetupLayout.getChildren().addAll(difficultySelector, sizeSelector, playButton2);
        gameSetupLayout.setAlignment(Pos.CENTER);
        gameSetupLayout.setPadding(new Insets(50));

        playButton2.setOnAction(event -> {
            RadioButton diffButton = (RadioButton) difficultyGroup.getSelectedToggle();
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
            lia = dungeon.getPlayer();

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

        ability = new Label("Ability : " + lia.getAbility());
        ability.setPadding(new Insets(5));
        ability.setBackground(labelBackground);

        endurance = new Label("Endurance : " + lia.getEndurance());
        endurance.setPadding(new Insets(5));
        endurance.setBackground(labelBackground);

        luck = new Label("Luck : " + lia.getLuck());
        luck.setPadding(new Insets(5));
        luck.setBackground(labelBackground);

        bagCapacity = new Label("Bag capacity : " + lia.getBagCapacity());
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

        movementButtons = new GridPane();
        movementButtons.setPadding(new Insets(2));
        movementButtons.setHgap(2);
        movementButtons.setVgap(2);
        movementButtons.add(upButton, 1, 0);
        movementButtons.add(leftButton, 0, 1);
        movementButtons.add(downButton, 1, 1);
        movementButtons.add(rightButton, 2, 1);

        gameLayout.add(movementButtons, 0, 2);

        //text area
        logsArea = new TextArea();
        logsArea.setEditable(false);
        logsArea.setText("You entered the dungeon.\n");

        gameLayout.add(logsArea, 1, 2);

        //game grid pane
        dungeon.fillPane();
        gamePane = dungeon.getGamePane(); //no padding, no hgap/vgap

        gameLayout.add(gamePane, 1, 0);
        gameLayout.setBackground(gameBackground);

        upButton.setOnAction(event -> dungeon.move(Direction.UP));
        leftButton.setOnAction(event -> dungeon.move(Direction.LEFT));
        downButton.setOnAction(event -> dungeon.move(Direction.DOWN));
        rightButton.setOnAction(event -> dungeon.move(Direction.RIGHT));

        gameScene = new Scene(gameLayout, WIDTH, HEIGHT);

        gameScene.setOnKeyPressed(event -> {
            //KeyCode.Z
            //KeyCode.ESCAPE
            switch (event.getCode()){
                case ESCAPE :
                    window.setScene(pauseMenu);
                    break;
                case P:
                    dungeon.revealAll();
                    newLog("The whole dungeon suddenly revealed itself !");
                    break;
                case Z:
                    dungeon.move(Direction.UP);
                    break;
                case Q:
                    dungeon.move(Direction.LEFT);
                    break;
                case S:
                    dungeon.move(Direction.DOWN);
                    break;
                case D:
                    dungeon.move(Direction.RIGHT);
                    break;
            }
            updateLabels();

        });
        dungeon.setPlayerTexture();
    }

    /**
     * Creates and shows the final scene of a game finished by an escape of the dungeon
     */
    public void createEndScene(){
        AnchorPane endLayout = new AnchorPane();

        //buttons
        MyButton exitButton = new MyButton("Exit to main menu");
        MyButton quitApp = new MyButton("Exit to desktop");


        Label endLabel = new Label("You escaped !");
        endLabel.setBackground(labelBackground);
        endLabel.setFont(Font.font(25));

        VBox buttonBox = new VBox(5);

        buttonBox.setLayoutX(WIDTH / 2.0 - 100);
        buttonBox.setLayoutY(100);

        buttonBox.getChildren().addAll(endLabel, logsArea, exitButton, quitApp);
        endLayout.getChildren().add(buttonBox);

        exitButton.setOnAction(event -> window.setScene(mainMenu));
        quitApp.setOnAction(event -> window.close());

        Scene endScene = new Scene(endLayout, WIDTH, HEIGHT);

        endScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                window.setScene(mainMenu);
        });




        endLayout.setBackground(gameBackground);

        window.setScene(endScene);
    }

    /**
     * Creates and shows the final scene of a game finished by a game over
     */
    public void gameOver(){
        GridPane endLayout = new GridPane();

        endLayout.setPadding(new Insets(10));
        endLayout.setHgap(10);


        //buttons
        MyButton exitButton = new MyButton("Exit to main menu");
        MyButton quitApp = new MyButton("Exit to desktop");
        Label endLabel = new Label("Game Over");
        endLabel.setBackground(labelBackground);
        endLabel.setFont(Font.font(25));

        VBox buttonBox = new VBox(5);

        buttonBox.setLayoutX(WIDTH / 2.0 - 100);
        buttonBox.setLayoutY(100);

        buttonBox.getChildren().addAll(endLabel, logsArea, exitButton, quitApp);
        endLayout.add(buttonBox, 0, 0);
        endLayout.add(gamePane,1 ,0);

        dungeon.revealAll();

        exitButton.setOnAction(event -> window.setScene(mainMenu));
        quitApp.setOnAction(event -> window.close());

        Scene endScene = new Scene(endLayout, WIDTH, HEIGHT);

        endScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                window.setScene(mainMenu);
        });


        endLayout.setBackground(gameBackground);

        window.setScene(endScene);
    }

    /**
     * Shows a new line in the logsArea
     *
     * @param log new text line
     */
    public void newLog(String log){
        logsArea.setText(logsArea.getText() + log + "\n");
    }

    /**
     * Refreshes the statistics labels of the player
     */
    private void updateLabels(){
        ability.setText("Ability : " + lia.getAbility());
        endurance.setText("Endurance : " + lia.getEndurance());
        luck.setText("Luck : " + lia.getLuck());
        bagCapacity.setText("Bag capacity : " + lia.getBagCapacity());

    }

    /**
     * Modifies the layout of the game scene to show the looting phase of the game. Also gives the possibility to cheat the looting phase by clicking the "show solution" button
     */
    public void createLootingScene(){
        gameLayout.getChildren().remove(movementButtons);
        gameLayout.getChildren().remove(dungeon.getGamePane());

        MyButton leaveButton = new MyButton("Leave");
        MyButton showSolution = new MyButton("Show Solution");

        gameLayout.add(leaveButton, 0, 2);
        gameLayout.add(showSolution, 0, 3);

        FlowPane lootingPane = new FlowPane();
        CheckBox temp;

        for(TreasureRoom.Element El : dungeon.getTreasureRoom().getElements()){
            temp = new CheckBox();
            temp.setUserData(El);
            temp.setText(El.getDescription() + " is worth " + El.getValue() + " crowns. It weights " + El.getSizeInBag() + " ounces");
            lootingPane.getChildren().add(temp);
        }

        lootingPane.setOnMouseEntered(event -> {
            try {
                int[] results = calculateOutcome(lootingPane);
                leaveButton.setText("Leave (" + results[0] + " crowns)");
            }catch (IOException e){
                leaveButton.setText("Bag too heavy !");
            }
        });
        lootingPane.setOnMouseExited(event -> {
            try {
                int[] results = calculateOutcome(lootingPane);
                leaveButton.setText("Leave (" + results[0] + " crowns)");
            }catch (IOException e){
                leaveButton.setText("Bag too heavy !");
            }
        });

        lootingPane.setBackground(labelBackground);

        gameLayout.add(lootingPane,1,0);

        leaveButton.setOnAction(event -> {
            int[] results;
            try{
                results = calculateOutcome(lootingPane);
                newLog("The total value of your bag is : " + results[0] + " crowns");
                createEndScene();
            }catch (IOException e){
                newLog(e.getMessage());
            }
        });

        showSolution.setOnAction(event -> {
            newLog("A possible solution could be to take the following items : ");

            LinkedList<TreasureRoom.Element> bestOrder = dungeon.getTreasureRoom().bestElementOrder();

            int bagCapacity1 = lia.getBagCapacity();

            while(bestOrder.size() > 0 && bagCapacity1 > 0){

                TreasureRoom.Element current = bestOrder.remove();

                if(current.getSizeInBag() < bagCapacity1){

                    newLog("  - " + current.getDescription() + " which is worth " + current.getValue() + " crowns and weights " + current.getSizeInBag() + " ounces.");
                    bagCapacity1 -= current.getSizeInBag();

                }
            }
        });


    }

    /**Function used to determinate what is the total value and weight of the selected treasures in the treasure room.
     *
     * @param pane the pane where the checkboxes are instanced.
     * @return two integers in a list. The first integer is the total value of the looting and the second is the total weight.
     * @throws IOException when the total size of collected elements is bigger than the player's bag capacity.
     */
    private int[] calculateOutcome(FlowPane pane) throws IOException {
        CheckBox temp;
        int totalValue = 0;
        int totalWeight = 0;
        for(int i = 0; i < pane.getChildren().size(); i++){
            temp = (CheckBox) pane.getChildren().get(i);
            if(temp.isSelected()){
                TreasureRoom.Element el = (TreasureRoom.Element)temp.getUserData();
                totalValue += el.getValue();
                totalWeight += el.getSizeInBag();
            }
        }
        if(totalWeight <= lia.getBagCapacity())
            return new int[]{totalValue, totalWeight};
        throw new IOException("Bag too small, select fewer items");
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