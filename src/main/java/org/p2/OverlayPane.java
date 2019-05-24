package org.p2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;



public class OverlayPane extends BorderPane {

    public SuggestionManager suggestionManager;

    public OverlayPane(double width, double height) {
        this.setPrefSize(width, height);
        this.setPadding(new Insets(80, 0, 0, 0));
        this.setPickOnBounds(false);
//        this.setMouseTransparent(true);
        suggestionManager = new SuggestionManager(width, height);
        this.setId("overlayPane");
        this.setRight(suggestionManager);
        this.InitializeRewardOMeter();
    }

    public void InitializeRewardOMeter(){
        VBox notificationManager = new VBox();
        this.setLeft(notificationManager);

        notificationManager.setAlignment(Pos.BOTTOM_LEFT);

        Pane notificationPane = new Pane();

        notificationManager.getChildren().add(notificationPane);

        notificationPane.getChildren().addAll(
                suggestionManager.getSuggestionbyFunction("bold").rewardNotification,
                suggestionManager.getSuggestionbyFunction("italic").rewardNotification,
                suggestionManager.getSuggestionbyFunction("underline").rewardNotification,
                suggestionManager.getSuggestionbyFunction("strikethrough").rewardNotification,
                suggestionManager.getSuggestionbyFunction("insertimage").rewardNotification,
                suggestionManager.getSuggestionbyFunction("align-right").rewardNotification,
                suggestionManager.getSuggestionbyFunction("align-center").rewardNotification,
                suggestionManager.getSuggestionbyFunction("align-left").rewardNotification,
                suggestionManager.getSuggestionbyFunction("align-justify").rewardNotification
        );





    }

}
