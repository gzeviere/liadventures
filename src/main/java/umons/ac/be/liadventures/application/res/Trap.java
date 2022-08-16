package umons.ac.be.liadventures.application.res;

public class Trap extends Cell{
    private final String pathToTexture = "file:src/main/resources/textures/sprites/trap.png";

    @Override
    public void reveal(){
        this.setStyle("-fx-background-color: #733473; -fx-border-color: black; -fx-background-image: url("+ pathToTexture+ ");");
    }
}
