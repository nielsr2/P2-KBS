package org.fxmisc.richtext.demo.richtext;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.*;

public class OverlayPane extends BorderPane {

    public KBSManager km;

    OverlayPane() {
        this.setPrefSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        this.setPadding(new Insets(80, 0, 0, 0));
        this.setStyle("-fx-border-color: black");
        this.setPickOnBounds(false);
        this.setMouseTransparent(true); // TODO MIKKEL, SO WITH THIS DISABLED, WE CANNOT CLICK TOOLBAR ICONS, WITH IT ENABLED WE
        // CANNOT CLICK KBS'. U go make clever code. nielz fixed it
        km = new KBSManager();
        this.setId("overlayPane");
        this.setRight(km);
        this.InitializeRewardOMeter();
    }
    KBSManager giveKM(){
        return this.km; // TODO i dont think this is neccessary anymore. check n delete?
    }

    public void InitializeRewardOMeter(){
        VBox rewardOMeterManager = new VBox();
        this.setLeft(rewardOMeterManager);

        rewardOMeterManager.setAlignment(Pos.BOTTOM_LEFT);

        Pane rewardOMeterPane = new Pane();

        rewardOMeterManager.getChildren().add(rewardOMeterPane);

        rewardOMeterPane.getChildren().addAll(
                km.getKBSbyFunction("bold").rewardOMeter,
                km.getKBSbyFunction("italic").rewardOMeter,
                km.getKBSbyFunction("underline").rewardOMeter,
                km.getKBSbyFunction("strikethrough").rewardOMeter,
                km.getKBSbyFunction("insertimage").rewardOMeter,
                km.getKBSbyFunction("align-right").rewardOMeter,
                km.getKBSbyFunction("align-center").rewardOMeter,
                km.getKBSbyFunction("align-left").rewardOMeter,
                km.getKBSbyFunction("align-justify").rewardOMeter
        );





    }

}
