package umons.ac.be.liadventures.application.res;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Random;

public class Dungeon {

    public Cell[][] data;
    private final Difficulty dif;
    private final TreasureRoom treasureRoom;
    private final GridPane gamePane;

    public Dungeon(int size, Difficulty dif){
        data = new Cell[size][size];
        this.dif = dif;
        gamePane = new GridPane();

        treasureRoom = new TreasureRoom();

        fill(dif);
    }

    public String getSize(){
        //return new int[]{data.length(), data[0].length()};
        return data.length + " " + data[0].length;
    }

    public GridPane getGamePane() {
        return gamePane;
    }

    public void fillPane(){
        Pane cellPane;
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[0].length; j++){
                cellPane = new Pane();
                cellPane.setPrefHeight(75);
                cellPane.setPrefWidth(75);
                cellPane.setStyle("-fx-background-color: #a5a5a5; -fx-border-color: black;");
                gamePane.add(cellPane, i,j);
            }
        }
    }

    private void fill(Difficulty dif){

        Random rand = new Random();

        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[0].length; j++){
                data[i][j] = randomCell(dif);
            }
        }

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
