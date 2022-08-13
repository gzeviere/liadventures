package umons.ac.be.liadventures.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.io.InputStream;

public class MyButton extends javafx.scene.control.Button {

    private final String FONT_PATH = "src/main/resources/Hack.ttf";

    private final String BUTTON_STYLE = "-fx-background-color: transparent; -fx-border-color: transparent; -fx-background-image : url(file:src/main/resources/textures/menus/button.png)";
    private final String BUTTON_HOVER_STYLE = "-fx-background-color: transparent; -fx-border-color: transparent; -fx-background-image : url(file:src/main/resources/textures/menus/buttonHover.png)";

    public MyButton(String text){

        InputStream is = null;
        try {
            is = new FileInputStream(FONT_PATH);
        } catch (Exception e){
            e.printStackTrace();
        }

        setText(text);
        setFont(Font.loadFont(is, 15));
        setPrefWidth(200);
        setPrefHeight(50);
        setStyle(BUTTON_STYLE);
        initializeButtonListeners();
    }
    protected void initializeButtonListeners(){
        setOnMouseEntered(event -> setStyle(BUTTON_HOVER_STYLE));
        setOnMouseExited(event -> setStyle(BUTTON_STYLE));
    }

    public static class MovementButton extends MyButton{
        private final String SMALL_BUTTON_STYLE = "-fx-background-color: transparent; -fx-border-color: transparent; -fx-background-image : url(file:src/main/resources/textures/menus/smallButton.png)";
        private final String SMALL_BUTTON_HOVER_STYLE = "-fx-background-color: transparent; -fx-border-color: transparent; -fx-background-image : url(file:src/main/resources/textures/menus/smallButtonHover.png)";

        public MovementButton(String text) {
            super(text);
            this.setPrefHeight(50);
            this.setPrefWidth(60);
            this.setFont(Font.font(25));
            this.setStyle(SMALL_BUTTON_STYLE);
            this.initializeButtonListeners();
        }
        protected void initializeButtonListeners(){
            setOnMouseEntered(event -> setStyle(SMALL_BUTTON_HOVER_STYLE));
            setOnMouseExited(event -> setStyle(SMALL_BUTTON_STYLE));
        }
    }

}
