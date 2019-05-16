package org.fxmisc.richtext.demo.richtext;

import com.fxexperience.javafx.animation.BounceOutRightTransition;
import com.fxexperience.javafx.animation.FadeInLeftTransition;
import com.fxexperience.javafx.animation.FadeInRightTransition;
import com.fxexperience.javafx.animation.FadeOutTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static javafx.scene.text.TextAlignment.CENTER;
import static org.fxmisc.richtext.demo.richtext.UIColors.setSkillOMeterColor;


public class SkillOMeter extends StackPane implements UIColors {

    Rectangle backgroundRect;

    int width = 180;
    int height = 49;
    boolean isBeingAnimatedConvince = false;
    private boolean isBeingAnimatedReward = false;


    Text textStart;
    Text textEnd;
    Text textMiddle;
    Text textFunctionality;
    TextFlow textFlow;
    private Color skillNrColor;
    private double skillNr;
    private Text skillNrText = new Text();
    private String skillNrString;
    public boolean rewardShown = false;

    private int tbTimesClickedInstance;
    private int kbsTimesUsedInstance;

    SkillOMeter(Text textStart, Text textMiddle, Text textEnd, Text textFunctionality, Color skillNrColor) {

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

        //System.out.println(getFont)

        textStart.setFont(Font.font("San Fransisco", 12));
        textMiddle.setFont(Font.font("San Fransisco", 12));
        textFunctionality.setFont(Font.font("San Fransisco", FontWeight.BOLD, 12));
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

    SkillOMeter(Text textStart, Text textEnd, Color skillNrColor) {

        this.textStart = textStart;
        this.textEnd = textEnd;
        this.skillNrColor = skillNrColor;


        this.setVisible(false);
        this.setManaged(false);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(0, 0, 0, 5));

        textFlow = new TextFlow(textStart, skillNrText, textEnd);

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

    public void tbTimesClickedInstance(int tbTimesClickedInstance) {
        this.tbTimesClickedInstance = tbTimesClickedInstance;
    }

    public void setKbsTimesUsedInstance(int kbsTimesUsedInstance) {
        this.kbsTimesUsedInstance = kbsTimesUsedInstance;
    }

    public void showText() {
        skillNrString = Double.toString(this.skillNr);
        skillNrText.setText(skillNrString);
    }

    public double getSkillNr() {
        return this.skillNr;
    }

    public void setSkillNr(double skillNr) {
        BigDecimal bd = new BigDecimal(skillNr).setScale(1, RoundingMode.HALF_UP);
        double newInput = bd.doubleValue();
        this.skillNr = newInput;
    }

    public void manageConvinceOMeter(double lowerThreshold, double upperThreshold) {

        if (tbTimesClickedInstance > 1 && getSkillNr() < upperThreshold && getSkillNr() > lowerThreshold && !this.isBeingAnimatedConvince) {
            setVisible(true);
            setManaged(true);
            showText();
            animateConvince();
        } else if (!this.isBeingAnimatedConvince){
            setVisible(false);
            setManaged(false);
        }
    }

    public void manageRewardOMeter(double lowerThreshold, double upperThreshold) {


        if (kbsTimesUsedInstance > 2 && getSkillNr() < upperThreshold && getSkillNr() > lowerThreshold && !this.isBeingAnimatedReward && rewardShown == false) {
            setVisible(true);
            setManaged(true);
            showText();
            animateReward();
            rewardShown = true;
        } else if (!this.isBeingAnimatedReward){
            setVisible(false);
            setManaged(false);
        }
    }

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

    public void animateReward() {

        this.isBeingAnimatedReward = true;
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
            this.isBeingAnimatedReward = false;
        });
    }


    public void hide() {

        BounceOutRightTransition Anim = new BounceOutRightTransition(this);
        Anim.setOnFinished(event -> {
            setVisible(false);
            setManaged(false);
//            resetGrow(); // reset size + translation
        });
        Anim.play();
    }
}
