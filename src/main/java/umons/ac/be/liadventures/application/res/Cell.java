package umons.ac.be.liadventures.application.res;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Cell extends Pane {
    //protected String pathToTexture; don't need as doesn't have texture
    public Cell(){}

    public void reveal(){
        this.setStyle("-fx-background-color: #ffffff; -fx-border-color: black;");
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
