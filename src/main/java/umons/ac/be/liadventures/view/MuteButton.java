package umons.ac.be.liadventures.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MuteButton extends Button {
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
