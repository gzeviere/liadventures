package umons.ac.be.liadventures.application.res;

import javafx.scene.layout.Pane;

public class Cell extends Pane {
    //protected String pathToTexture; don't need as doesn't have texture
    public Cell(){}

    /**
     * Function called to reveal the cell once the player stepped in this cell.
     */
    public void reveal(){
        this.setStyle("-fx-background-color: #ffffff; -fx-border-color: black;");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
