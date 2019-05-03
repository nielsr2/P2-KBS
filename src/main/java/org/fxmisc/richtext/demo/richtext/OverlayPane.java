package org.fxmisc.richtext.demo.richtext;

import javafx.scene.layout.BorderPane;

public class OverlayPane extends BorderPane {

    public KBSManager km;

    OverlayPane() {
        this.setPrefSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
//        this.setMouseTransparent(true);
        KBSManager km = new KBSManager();
        this.setRight(km);
    }
}
