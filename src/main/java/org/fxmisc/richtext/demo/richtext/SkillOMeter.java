package org.fxmisc.richtext.demo.richtext;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static javafx.scene.text.TextAlignment.CENTER;


public class SkillOMeter extends StackPane implements UIColors {

    Rectangle backgroundRect;

    int width = 170;
    int height = 49;


    Text textStart = new Text("When not using shortcuts, \n you are ");
    Text textEnd = new Text(" slower!");
    TextFlow textFlow;
    private Color skillNrColor;
    private double skillNr;
    private Text skillNrText = new Text();
    private String skillNrString;

    private int tbTimesClickedInstance;
    private int kbsTimesUsedInstance;

    SkillOMeter(Text textStart, Text textEnd, Color skillNrColor) {

        this.textStart = textStart;
        this.textEnd = textEnd;
        this.skillNrColor = skillNrColor;


        this.setVisible(false);
        this.setManaged(false);
        this.setAlignment(Pos.CENTER);

        textFlow = new TextFlow(textStart, skillNrText, textEnd);

        textFlow.setPrefWidth(width);
        textFlow.setTextAlignment(CENTER);
        textFlow.setPadding(new Insets(7, 10, 7, 10));

        //System.out.println(getFont)

        textStart.setFont(Font.font("Sergoe UI", 12));
        skillNrText.setFont(Font.font("Sergoe UI", FontWeight.BOLD, 12));
        skillNrText.setFill(this.skillNrColor);
        textEnd.setFont(Font.font("Sergoe UI", 12));

        backgroundRect = new Rectangle(width, height, UIColors.setSkillOMeterColor());
        backgroundRect.setArcHeight(100);
        backgroundRect.setArcWidth(40);
        backgroundRect.setStroke(borderColor);

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

        if (tbTimesClickedInstance > 1 && getSkillNr() < upperThreshold && getSkillNr() > lowerThreshold) {
            setVisible(true);
            setManaged(true);
            showText();
        } else {
            setVisible(false);
            setManaged(false);
        }
    }

    public void manageRewardOMeter(double lowerThreshold, double upperThreshold) {

        if (kbsTimesUsedInstance > 1 && getSkillNr() < upperThreshold && getSkillNr() > lowerThreshold) {
            setVisible(true);
            setManaged(true);
            showText();
        } else {
            setVisible(false);
            setManaged(false);
        }
    }


}
