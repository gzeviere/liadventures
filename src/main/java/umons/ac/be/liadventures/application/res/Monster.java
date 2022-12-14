package umons.ac.be.liadventures.application.res;

import java.util.Random;

public class Monster extends Cell {
    private final String pathToTexture = "file:src/main/resources/textures/sprites/monster.png";

    private final int ability;
    private int endurance;
    public boolean isDead;


    /**
     * Generates a new monster and sets randoms stats for it matching the probability of dice rolls :
     *   Ability = two rolled dice,
     *   Endurance = two rolled dice.
     */
    public Monster(){
        isDead = false;
        Random rand = new Random();
        this.ability = 2 + rand.nextInt(11); //2 < ability < 12
        this.endurance = 2 + rand.nextInt(11); //2 < endurance < 12
    }

    @Override
    public void reveal(){
        this.setStyle("-fx-background-color: #b92626; -fx-border-color: black; -fx-background-image: url(" + pathToTexture + ");");
    }

    public int getAbility() {
        return ability;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }
}
