package umons.ac.be.liadventures.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MuteButton extends MyButton {
    public MuteButton(){
        super("");
        setPrefWidth(40);
        setPrefHeight(40);
        initializeButtonListeners();

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // mute son
            }
        });
    }




}
