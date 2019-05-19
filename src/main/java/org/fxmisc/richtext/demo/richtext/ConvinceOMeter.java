package org.fxmisc.richtext.demo.richtext;

import com.fxexperience.javafx.animation.FadeInRightTransition;
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
import static org.fxmisc.richtext.demo.richtext.UIColors.setSkillOMeterColor;

public class ConvinceOMeter extends SkillOMeter implements UIColors {

    protected boolean isBeingAnimatedConvince = false;
    int width = 180;
    int height = 49;

    ConvinceOMeter(Text textStart, Text textEnd, Color skillNrColor) {

        this.textStart = textStart;
        this.textEnd = textEnd;
        this.skillNrColor = skillNrColor;


        this.setVisible(false);
        this.setManaged(false);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(0, 0, 0, 5));

        this.textFlow = new TextFlow(textStart, skillNrText, textEnd);

        textFlow.setPrefWidth(width);
        textFlow.setTextAlignment(CENTER);
        textFlow.setPadding(new Insets(7, 10, 7, 10));

        //System.out.println(getFont)

        textStart.setFont(Font.font("San Fransisco", 12));
        skillNrText.setFont(Font.font("San Fransisco", FontWeight.BOLD, 12));
        skillNrText.setFill(this.skillNrColor);
        textEnd.setFont(Font.font("San Fransisco", 12));

        backgroundRect = new Rectangle(width, height, setSkillOMeterColor());
        backgroundRect.setArcHeight(100);
        backgroundRect.setArcWidth(40);
        backgroundRect.setStroke(borderColor);
        backgroundRect.setOpacity(0.8);

        this.getChildren().addAll(backgroundRect, textFlow);
    }

    public ConvinceOMeter() {
    }

    // Controls when object is shown and hidden
    public void manageConvinceOMeter(double lowerThreshold, double upperThreshold) {

        if (tbTimesClickedInstance > 1 && getSkillNr() < upperThreshold && getSkillNr() > lowerThreshold && !this.isBeingAnimatedConvince) {
            setVisible(true);
            setManaged(true);
            showText();
            animateConvince();
        } else if (!this.isBeingAnimatedConvince) {
            setVisible(false);
            setManaged(false);
        }
    }

    //animates object
    public void animateConvince() {

        this.isBeingAnimatedConvince = true;
        System.out.println("Hi there! Now I'm hidden!");
        FadeInRightTransition fadeInRightTransition = new FadeInRightTransition(this);
        FadeOutTransition fadeOutTransition = new FadeOutTransition(this);

        fadeInRightTransition.setRate(0.8);
        fadeInRightTransition.play();

        fadeInRightTransition.setOnFinished(eventStart -> {
            fadeOutTransition.setDelay(Duration.millis(3000));
            fadeOutTransition.setRate(0.2);
            fadeOutTransition.play();
        });

        fadeOutTransition.setOnFinished(eventEnd -> {
            setVisible(false);
            setManaged(false);
            this.isBeingAnimatedConvince = false;
        });
    }

}