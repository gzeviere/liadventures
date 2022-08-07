package umons.ac.be.liadventures.view;

import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;

public class SubScene extends javafx.scene.SubScene {

    public SubScene() {
        super(new AnchorPane(), 600, 400);
        prefWidth(600);
        prefHeight(400);

        AnchorPane subScene = (AnchorPane) this.getRoot();

    }
}
