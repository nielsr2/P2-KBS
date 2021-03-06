package org.p2;

import com.fxexperience.javafx.animation.FadeInLeftTransition;
import com.fxexperience.javafx.animation.FadeOutTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import static javafx.scene.text.TextAlignment.CENTER;
import static org.p2.UIColors.getNotificationsColor;

public class RewardNotification extends Notifications {

    int width = 180;
    int height = 49;

    protected boolean shown;
    private boolean isBeingAnimated = false;

    RewardNotification(Text textStart, Text textMiddle, Text textEnd, Text textFunctionality, Color skillNrColor) {

        this.textStart = textStart;
        this.textMiddle = textMiddle;
        this.textEnd = textEnd;
        this.textFunctionality = textFunctionality;
        this.skillNrColor = skillNrColor;

        this.setVisible(false);
        this.setManaged(false);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(0, 0, 0, 5));

        textFlow = new TextFlow(textStart, textFunctionality, textMiddle, skillNrText, textEnd);

        textFlow.setPrefWidth(width);
        textFlow.setTextAlignment(CENTER);
        textFlow.setPadding(new Insets(7, 10, 7, 10));

        textStart.setFont(Font.font("San Fransisco", 12));
        textMiddle.setFont(Font.font("San Fransisco", 12));
        textFunctionality.setFont(Font.font("San Fransisco", FontWeight.BOLD, 12));
        skillNrText.setFont(Font.font("San Fransisco", FontWeight.BOLD, 12));
        skillNrText.setFill(this.skillNrColor);
        textEnd.setFont(Font.font("San Fransisco", 12));

        backgroundRect = new Rectangle(width, height, getNotificationsColor());
        backgroundRect.setArcHeight(100);
        backgroundRect.setArcWidth(40);
        backgroundRect.setStroke(UIColors.getBorderColor());
        backgroundRect.setOpacity(0.8);

        this.getChildren().addAll(backgroundRect, textFlow);


    }

    // Controls when object is shown and hidden
    public void manageNotification(double lowerThreshold, double upperThreshold) {

        if (kbsTimesUsedInstance > 2 && getSkillNr() < upperThreshold && getSkillNr() > lowerThreshold && !this.isBeingAnimated && shown == false) {
            setVisible(true);
            setManaged(true);
            showText();

            animate();
            shown = true;
        } else if (!this.isBeingAnimated) {
            setVisible(false);
            setManaged(false);
        }
    }

    // Animates Object
    public void animate() {

        this.isBeingAnimated = true;
        System.out.println("Hi there! Now I'm hidden!");
        FadeInLeftTransition fadeInLeftTransition = new FadeInLeftTransition(this);
        FadeOutTransition fadeOutTransition = new FadeOutTransition(this);

        fadeInLeftTransition.setRate(0.8);
        fadeInLeftTransition.play();

        fadeInLeftTransition.setOnFinished(eventStart -> {
            fadeOutTransition.setDelay(Duration.millis(3000));
            fadeOutTransition.setRate(0.2);
            fadeOutTransition.play();
        });

        fadeOutTransition.setOnFinished(eventEnd -> {
            setVisible(false);
            setManaged(false);
            this.isBeingAnimated = false;
        });
    }
}