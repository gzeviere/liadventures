package umons.ac.be.liadventures.application.res;

import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import umons.ac.be.liadventures.view.Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Random;

public class Dungeon {

    public Cell[][] data;
    private final TreasureRoom treasureRoom;
    private final GridPane gamePane;

    //player in the dungeon
    private final Player lia;

    public Dungeon(int size, Difficulty dif){
        data = new Cell[size][size];
        gamePane = new GridPane();

        lia = new Player();

        treasureRoom = new TreasureRoom();

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

            data[lia.getPosX()][lia.getPosY()].setStyle("-fx-background-color:white;-fx-border-color: black; -fx-background-image : url(file:src/main/resources/textures/sprites/player.png);");
            data[lastX][lastY].reveal();
            return true;
        }
        return false;
    }

    public void revealAll(){
        FileInputStream is = null;
        try{
            is = new FileInputStream("src/main/resources/textures/sprites/player.png");

        }catch(FileNotFoundException e ){
            //no pb
        }

        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[0].length; j++){
                data[i][j].reveal();
            }
        }

    }

    public void startGame(){
        data[0][0].setStyle(" -fx-background-color : white; -fx-border-color: black; -fx-background-image : url(file:src/main/resources/textures/sprites/player.png);");

        if(exploration()){
            looting();
        }
    }

    /**
     * Initiate the game loop, exploration phase of the game
     *
     * @return true if the player reached the treasure room, false if the player's endurance reached 0.
     */
    private boolean exploration(){
        Controller controller = Controller.getInstance();
        return false;
    }

    private void looting(){

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
            randCell= new Cell();
        else if (randInt < dif.emptyRate + dif.trapRate)
            randCell = new Trap();
        else
            randCell = new Monster();

        return randCell;
    }
}
