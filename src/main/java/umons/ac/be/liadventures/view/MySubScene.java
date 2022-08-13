package umons.ac.be.liadventures.view;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.InputStream;

public class MySubScene extends javafx.scene.SubScene {

    public MySubScene() {
        super(new AnchorPane(), 100, 100);
        prefWidth(100);
        prefHeight(100);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        setLayoutX(100);
        setLayoutY(100);

        InputStream is = null;
        try {
            is = new FileInputStream("src/main/resources/textures/menus/button.png");
        } catch (Exception e){
            e.printStackTrace();
        }
        Image backgroundImage = new Image(is, 100,100,false,true); //require non null
        BackgroundImage bckg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);

        root2.setBackground(new Background(bckg));
    }

    public void moveSubScene(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        transition.setToX(-600);
        transition.play();
    }
}
