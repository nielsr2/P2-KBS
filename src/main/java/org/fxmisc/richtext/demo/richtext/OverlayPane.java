package org.fxmisc.richtext.demo.richtext;

import javafx.scene.layout.BorderPane;

public class OverlayPane extends BorderPane {

    public KBSManager km;

    OverlayPane() {
        this.setPrefSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
//        this.setMouseTransparent(true); // TODO MIKKEL, SO WITH THIS DISABLED, WE CANNOT CLICK TOOLBAR ICONS, WITH IT ENABLED WE CANNOT CLICK KBS'. U go make clever code.
        km = new KBSManager();
        this.setId("overlayPane");
        this.setRight(km);
    }
    KBSManager giveKM(){
        return this.km;
    }

}
