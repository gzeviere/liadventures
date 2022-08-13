package umons.ac.be.liadventures.application.res;

import java.util.Random;

public class Monster extends Cell {
    private final String pathToTexture = "src/main/resources/textures/Monster.jpg";

    private int ability;
    private int endurance;

    public Monster(){
        Random rand = new Random();
        this.ability = 2 + rand.nextInt(11); //2 < ability < 12
        this.endurance = 2 + rand.nextInt(11); //2 < endurance < 12
    }

    public int getAbility() {
        return ability;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setAbility(int ability) {
        this.ability = ability;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }
}
