package org.fxmisc.richtext.demo.richtext;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

import java.awt.*;

public class OverlayPane extends BorderPane {

    public KBSManager km;

    OverlayPane() {
        this.setPrefSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        this.setPadding(new Insets(80, 0, 0, 0));
        this.setStyle("-fx-border-color: black");
        this.setPickOnBounds(false);
//        this.setMouseTransparent(true); // TODO MIKKEL, SO WITH THIS DISABLED, WE CANNOT CLICK TOOLBAR ICONS, WITH IT ENABLED WE CANNOT CLICK KBS'. U go make clever code. nielz fixed it
        km = new KBSManager();
        this.setId("overlayPane");
        this.setRight(km);
    }
    KBSManager giveKM(){
        return this.km;
    }

}
