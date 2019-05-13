package org.fxmisc.richtext.demo.richtext;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static javafx.scene.text.TextAlignment.CENTER;


public class ConvinceOMeter extends StackPane implements UIColors {

    Rectangle backgroundRect;

    int width = 170;
    int height = 49;

    private double timesSlower;
    Text textStart = new Text("When not using shortcuts, \n you are ");
    Text textEnd = new Text(" slower!");
    TextFlow textFlow;
    private Text timesSlowerText = new Text();
    private String timesSlowerString;

    private int tbTimesClickedInstance;

    public void tbTimesClickedInstance(int tbTimesClickedInstance) {
        this.tbTimesClickedInstance = tbTimesClickedInstance;
    }


    ConvinceOMeter() {


        this.setVisible(false);
        this.setManaged(false);
        this.setAlignment(Pos.CENTER);

        textFlow = new TextFlow(textStart, timesSlowerText, textEnd);

        textFlow.setPrefWidth(width);
        textFlow.setTextAlignment(CENTER);
        textFlow.setPadding(new Insets(7, 10, 7, 10));

        //System.out.println(getFont)

        textStart.setFont(Font.font("Sergoe UI", 12));
        timesSlowerText.setFont(Font.font("Sergoe UI", FontWeight.BOLD, 12));
        timesSlowerText.setFill(textAlertColor);
        textEnd.setFont(Font.font("Sergoe UI", 12));

        backgroundRect = new Rectangle(width, height, UIColors.setSkillOMeterColor());
        backgroundRect.setArcHeight(100);
        backgroundRect.setArcWidth(40);
        backgroundRect.setStroke(borderColor);

        this.getChildren().addAll(backgroundRect, textFlow);
    }

    public void showText() {
        timesSlowerString = Double.toString(this.timesSlower);
        timesSlowerText.setText(timesSlowerString);
    }

    public double getTimesSlower() {
        return this.timesSlower;
    }

    public void setTimesSlower(double timesSlower) {
        BigDecimal bd = new BigDecimal(timesSlower).setScale(1, RoundingMode.HALF_UP);
        double newInput = bd.doubleValue();
        this.timesSlower = newInput;
    }

    public void manageConvinceOMeter() {
        int upperThreshold = 8;
        double lowerThreshold = 1.5;
        if (tbTimesClickedInstance > 1 && getTimesSlower() < upperThreshold && getTimesSlower() > lowerThreshold) {
            setVisible(true);
            setManaged(true);
            showText();
        } else {
            setVisible(false);
            setManaged(false);
        }
    }



}
