package org.fxmisc.richtext.demo.richtext;

import com.fxexperience.javafx.animation.BounceOutRightTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.math.BigDecimal;
import java.math.RoundingMode;


public abstract class SkillOMeter extends StackPane {

    protected Rectangle backgroundRect;

    protected Text textStart;
    protected Text textEnd;
    protected Text textMiddle;
    protected Text textFunctionality;
    protected TextFlow textFlow;
    protected Color skillNrColor;
    protected double skillNr;
    protected Text skillNrText = new Text();
    protected String skillNrString;
    protected boolean rewardShown = false;

    protected int tbTimesClickedInstance;
    protected int kbsTimesUsedInstance;

    // Set times clicked on toolbar
    public void SetTbTimesClickedInstance(int tbTimesClickedInstance) {
        this.tbTimesClickedInstance = tbTimesClickedInstance;
    }

    // Set times KBS is used
    public void setKbsTimesUsedInstance(int kbsTimesUsedInstance) {
        this.kbsTimesUsedInstance = kbsTimesUsedInstance;
    }

    // set skillNr to text
    public void showText() {
        skillNrString = Double.toString(this.skillNr);
        skillNrText.setText(skillNrString);
    }

    // Get skillNr
    public double getSkillNr() {
        return this.skillNr;
    }

    // set SkillNr and rounds off
    public void setSkillNr(double skillNr) {
        BigDecimal bd = new BigDecimal(skillNr).setScale(1, RoundingMode.HALF_UP);
        double newInput = bd.doubleValue();
        this.skillNr = newInput;
    }

    // Hides object
    public void hide() {

        BounceOutRightTransition Anim = new BounceOutRightTransition(this);
        Anim.setOnFinished(event -> {
            setVisible(false);
            setManaged(false);
        });
        Anim.play();
    }
}
