package umons.ac.be.liadventures.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Button extends javafx.scene.control.Button {
    //String.valueOf(Button.class.getResourceAsStream("Hack.ttf"));

    private final String FONT_PATH = "src/main/resources/Hack.ttf";

    private final String BUTTON_STYLE = "-fx-background-color: #ececad;-fx-border-color:#6c6c6c;-fx-border-radius: 5;";
    private final String BUTTON_HOVER_STYLE = "-fx-background-color: lightyellow;-fx-border-color:grey;-fx-border-radius: 5;";

    public Button(String text){
        setText(text);
        setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        setPrefWidth(200);
        setPrefHeight(50);
        setStyle(BUTTON_STYLE);
        initializeButtonListeners();
    }
    protected void initializeButtonListeners(){
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStyle(BUTTON_HOVER_STYLE);
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStyle(BUTTON_STYLE);
            }
        });
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //change
            }
        });
    }

}
