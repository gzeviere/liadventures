package umons.ac.be.liadventures.application.res;

import javafx.scene.layout.GridPane;
import umons.ac.be.liadventures.view.Controller;

import java.util.Random;

public class Dungeon {

    public final Cell[][] data;
    private final TreasureRoom treasureRoom;
    private final GridPane gamePane;

    //the application controller (which is a singleton class)
    private final Controller controller;

    //player in the dungeon
    private final Player lia;

    /**
     * Generates a new dungeon, its only treasure room and the only player that will explore it.
     *
     * @param size of the generated dungeon (will be size x size).
     * @param dif the spawn rate difficulty of the traps and monsters.
     */
    public Dungeon(int size, Difficulty dif){
        data = new Cell[size][size];
        gamePane = new GridPane();

        lia = new Player();

        treasureRoom = new TreasureRoom();

        controller = Controller.getInstance();

        fill(dif);
    }

    public Player getPlayer() {
        return lia;
    }

    public GridPane getGamePane() {
        return gamePane;
    }

    /**
     * Fills the javaFX GridPane used to display the dungeon's cells with hidden grey cells.
     */
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

    /**
     * Moves the player inside the dungeon matching the parametrized direction dir.
     * @param dir Direction the player moves.
     */
    public void move(Direction dir) {
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

            setPlayerTexture();

            data[lastX][lastY].reveal();

            if(! interactCurrentCell())
                controller.gameOver();

        }
    }

    /**
     * Replaces the texture of the cell the player is in with the player model/sprite. Aware of what's in the cell originally
     *
     */
    public void setPlayerTexture() {
        int x = lia.getPosX();
        int y = lia.getPosY();
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

    /**
     * Reveals all the cells of the dungeon.
     */
    public void revealAll(){
        for (Cell[] datum : data) {
            for (int j = 0; j < data[0].length; j++) {
                datum[j].reveal();
            }
        }
        setPlayerTexture();
    }


    /**
     * Engages the looting phase of the game.
     */
    public void looting(){
        controller.newLog("You entered the treasure room ! You could profit at least " + treasureRoom.bestPossibleOutcome(lia.getBagCapacity()));
        controller.createLootingScene();
    }

    /**
     * Makes the player interact with the cell he is in. i.e. if the player is in a Monster cell, this function engages the fight with the monster.
     *
     * @return false if the player endurance is < 0 (end of the game)
     */
    private boolean interactCurrentCell(){
        int liaPosX = lia.getPosX();
        int liaPosY = lia.getPosY();

        switch(data[liaPosX][liaPosY].getClass().getSimpleName()){
            case "Monster":
                if(((Monster) data[liaPosX][liaPosY]).isDead)
                    break;
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
                    break;
                }
                controller.newLog("Woosh ! You saw a trap before it triggered and avoided it ! You lose 1 luck");
                break;
            case "TreasureRoom":
                looting();
                break;
        }
        return true;

    }

    /**
     * Putting traps and monsters in the dungeon matching the parametrized difficulty spawn rates. Also makes the entry an empty cell and placing randomly a treasure room.
     *
     * @param dif Difficulty of the dungeon
     */
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

    /**
     * Function used to get a random cell type matching the parametrized difficulty.
     *
     * @param dif Difficulty of the dungeon.
     * @return a randomly generated Cell.
     */
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
