package umons.ac.be.liadventures.application.res;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import umons.ac.be.liadventures.view.Controller;

import java.util.Random;

public class Dungeon {

    public Cell[][] data;
    private final TreasureRoom treasureRoom;
    private final GridPane gamePane;
    private Pane lootingPane;
    private final Stage window;

    Controller controller;

    //player in the dungeon
    private final Player lia;

    public Dungeon(int size, Difficulty dif){
        data = new Cell[size][size];
        gamePane = new GridPane();

        lia = new Player();

        treasureRoom = new TreasureRoom();

        controller = Controller.getInstance();
        window = controller.getWindow();

        fill(dif);
    }

    public Player getLia() {
        return lia;
    }

    public GridPane getGamePane() {
        return gamePane;
    }

    public void fillPane(){
        Cell cellPane;
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[0].length; j++){
                cellPane = data[i][j];
                cellPane.setPrefHeight(75);
                cellPane.setPrefWidth(75);
                cellPane.setStyle("-fx-background-color: #a5a5a5; -fx-border-color: black;");
                gamePane.add(cellPane, i,j);
            }
        }
    }

    public boolean move(Direction dir) {
        int lastX = lia.getPosX();
        int lastY = lia.getPosY();

        int x = lastX;
        int y = lastY;

        switch (dir) {
            case UP:
                y--;
                break;
            case LEFT:
                x--;
                break;
            case DOWN:
                y++;
                break;
            case RIGHT:
                x++;
                break;
        }
        if (x < data.length && x >= 0 && y < data.length && y >= 0){
            lia.setPosX(x);
            lia.setPosY(y);

            setPlayerTexture(x, y);

            data[lastX][lastY].reveal();

            if(! interactCurrentCell())
                playerDied();

            return false;
        }
        return false;
    }

    private void setPlayerTexture(int x, int y) {
        switch(data[x][y].getClass().getSimpleName()){
            case "Monster":
                data[x][y].setStyle("-fx-background-color:#b92626;-fx-border-color: black; -fx-background-image : url(file:src/main/resources/textures/sprites/player.png);");
                break;
            case "Trap":
                data[x][y].setStyle("-fx-background-color:#733473;-fx-border-color: black; -fx-background-image : url(file:src/main/resources/textures/sprites/player.png);");
                break;
            case "TreasureRoom":
                data[x][y].setStyle("-fx-background-color:#ffae00;-fx-border-color: black; -fx-background-image : url(file:src/main/resources/textures/sprites/player.png);");
                break;
            default:
                data[x][y].setStyle("-fx-background-color:white;-fx-border-color: black; -fx-background-image : url(file:src/main/resources/textures/sprites/player.png);");
                break;
        }
    }

    public void playerDied(){
        controller.gameOver();
        //controller.createEndScene("Game Over !");
    }

    public void revealAll(){
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[0].length; j++){
                data[i][j].reveal();
            }
        }
        int x = lia.getPosX();
        int y = lia.getPosY();
        setPlayerTexture(x, y);
    }

    public void startGame(){
        data[0][0].setStyle(" -fx-background-color : white; -fx-border-color: black; -fx-background-image : url(file:src/main/resources/textures/sprites/player.png);");
    }

    /**
     * Initiate the game loop, exploration phase of the game
     *
     * @return true if the player reached the treasure room, false if the player's endurance reached 0.
     */
    private boolean exploration(){

        return false;
    }

    public void looting(){
        controller.newLog("You entered the treasure room ! You could profit at least " + treasureRoom.bestPossibleOutcome(lia.getBagCapacity()));
        lootingPane = new Pane();

        controller.createLootingScene();
    }

    /**
     *
     * @return false if the player endurance is > 0 (end of the game)
     */
    private boolean interactCurrentCell(){
        int liaPosX = lia.getPosX();
        int liaPosY = lia.getPosY();

        switch(data[liaPosX][liaPosY].getClass().getSimpleName()){
            case "Monster":
                int enduranceBeforeFight = lia.getEndurance();
                lia.fightMonster((Monster) data[liaPosX][liaPosY]);
                if(lia.getEndurance() < 1) {
                    controller.newLog("The monster defeated you ... With " + ((Monster) data[liaPosX][liaPosY]).getEndurance() + " endurance left");
                    return false;
                }
                controller.newLog("You defeated a monster and lost " + (enduranceBeforeFight-lia.getEndurance()) + " Endurance");

                break;
            case "Trap":
                if(!lia.triggeredTrap()) {
                    if (lia.getEndurance() < 1) {
                        controller.newLog("Game Over ! You fell into a trap and succumbed to it");
                        return false;
                    }
                    controller.newLog("Ouch ! You fell into a trap and lost 2 endurance");
                }
                controller.newLog("Woosh ! You saw a trap before it triggered and avoided it ! You lose 1 luck");
                break;
            case "TreasureRoom":
                looting();
                break;
        }
        return true;

    }

    private void fill(Difficulty dif){

        Random rand = new Random();

        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[0].length; j++){
                data[i][j] = randomCell(dif);
            }
        }

        //dungeon entry
        data[0][0] = new Cell();

        int x, y;
        do {
            x = rand.nextInt(data.length);
            y = rand.nextInt(data[0].length);
        } while (x == 0 && y == 0);

        data[x][y] = treasureRoom;
    }

    private Cell randomCell(Difficulty dif){
        Random rand = new Random();
        int randInt = rand.nextInt(101);

        Cell randCell;

        if(randInt < dif.emptyRate)
            randCell = new Cell();
        else if (randInt < dif.emptyRate + dif.trapRate)
            randCell = new Trap();
        else
            randCell = new Monster();

        return randCell;
    }

    public TreasureRoom getTreasureRoom() {
        return treasureRoom;
    }
}
