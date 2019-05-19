package org.p2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static org.p2.Settings.WINDOW_WIDTH;

public class OverlayPane extends BorderPane {

    public KBSManager km;

    public OverlayPane() {
        this.setPrefSize(WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        this.setPadding(new Insets(80, 0, 0, 0));
        this.setPickOnBounds(false);
//        this.setMouseTransparent(true);
        km = new KBSManager();
        this.setId("overlayPane");
        this.setRight(km);
        this.InitializeRewardOMeter();
    }

    public void InitializeRewardOMeter(){
        VBox notificationManager = new VBox();
        this.setLeft(notificationManager);

        notificationManager.setAlignment(Pos.BOTTOM_LEFT);

        Pane notificationPane = new Pane();

        notificationManager.getChildren().add(notificationPane);

        notificationPane.getChildren().addAll(
                km.getKBSbyFunction("bold").rewardNotification,
                km.getKBSbyFunction("italic").rewardNotification,
                km.getKBSbyFunction("underline").rewardNotification,
                km.getKBSbyFunction("strikethrough").rewardNotification,
                km.getKBSbyFunction("insertimage").rewardNotification,
                km.getKBSbyFunction("align-right").rewardNotification,
                km.getKBSbyFunction("align-center").rewardNotification,
                km.getKBSbyFunction("align-left").rewardNotification,
                km.getKBSbyFunction("align-justify").rewardNotification
        );





    }

}
