package org.fxmisc.richtext.demo.richtext;

import javafx.scene.layout.BorderPane;

public class OverlayPane extends BorderPane {

    public KBSManager km;

    OverlayPane() {
        this.setPrefSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
//        this.setMouseTransparent(true);
        km = new KBSManager();
        this.setId("overlayPane");
        this.setRight(km);
    }
    KBSManager giveKM(){
        return this.km;
    }

}
