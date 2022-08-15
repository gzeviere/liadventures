package umons.ac.be.liadventures.application.res;

import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import umons.ac.be.liadventures.view.Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
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

    //dungeon's size is n x n.
    public int getSize(){
        //return new int[]{data.length(), data[0].length()};
        return data.length;
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


            System.out.println(data[lastX][lastY]);
            data[lastX][lastY].reveal();

            if(interactCurrentCell())
                return true;

            return false;
        }
        return false;
    }

    public void revealAll(){
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[0].length; j++){
                data[i][j].reveal();
            }
        }
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

    private void looting(){
        lootingPane = new Pane();

        treasureRoom.looting();
       controller.createLootingScene();
    }

    /**
     *
     * @return true if the player endurance is > 0
     */
    private boolean interactCurrentCell(){
        int liaposx = lia.getPosX();
        int liaposy = lia.getPosY();

        switch(data[liaposx][liaposy].getClass().getSimpleName()){
            case "Monster":
                lia.fightMonster((Monster) data[liaposx][liaposy]);
                if(lia.getEndurance() < 1)
                    return false;

                //TODO fix this
                break;
            case "Trap":
                lia.triggeredTrap();
                if(lia.getEndurance() < 1)
                    return false;
                break;
            case "TreasureRoom":
                looting();
                break;
        }
        System.out.println(lia.getEndurance());
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
