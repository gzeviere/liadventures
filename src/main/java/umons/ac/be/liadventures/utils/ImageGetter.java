package umons.ac.be.liadventures.utils;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.InputStream;

public class ImageGetter {
    public ImageGetter(){
    }

    public Image getImage(String path){ //path : "src/main/resources/textures/menus/background.jpg"
        InputStream is = null;
        try {
            is = new FileInputStream(path);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new Image(is);
    }
}
