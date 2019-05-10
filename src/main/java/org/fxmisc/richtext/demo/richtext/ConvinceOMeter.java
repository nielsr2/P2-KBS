package org.fxmisc.richtext.demo.richtext;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static javafx.scene.paint.Color.DARKGRAY;
import static javafx.scene.text.TextAlignment.CENTER;

public class ConvinceOMeter extends StackPane {

    Rectangle backgroundRect;

    int width = 170;
    int height = 50;

    private double timesSlower;
    Text textStart = new Text("You are ");
    Text textEnd = new Text(" times slower by \n not using this shortcut");
    TextFlow textFlow;
    private Text timesSlowerText = new Text();
    private String timesSlowerString;


    ConvinceOMeter(float timesSlower) {
        this.timesSlower = timesSlower;
        this.setVisible(false);
        this.setManaged(false);
        this.setAlignment(Pos.CENTER);

        textFlow = new TextFlow(textStart, timesSlowerText, textEnd);

        textFlow.setPrefWidth(width);
        textFlow.setTextAlignment(CENTER);
        textFlow.setPadding(new Insets(7, 10, 7, 10));

        timesSlowerText.setFill(Color.RED);

        backgroundRect = new Rectangle(width, height, Color.LIGHTGREY);
        backgroundRect.setArcHeight(100);
        backgroundRect.setArcWidth(40);
        backgroundRect.setStroke(DARKGRAY);

        this.getChildren().addAll(backgroundRect, textFlow);
    }

    public void showText() {

        //textStart.setWrappingWidth(width);

        timesSlowerString = Double.toString(this.timesSlower);
        timesSlowerText.setText(timesSlowerString);


        //this.getChildren().addAll(backgroundRect, textFlow);

        //(System.out.println(timesSlower);

        //text.setText("You are " + timesSlower.getText() + " times slower by \n not using this shortcut");


    }

    /*public TextFlow showText2() {
        Text textStart = new Text("You are ");
        Text textEnd = new Text(" times slower by \n not using this shortcut");

        textStart.setWrappingWidth(width);
        String string = Double.toString(this.timesSlower);
        Text timesSlower = new Text(string);
        timesSlower.setFill(Color.RED);

        TextFlow textFlow = new TextFlow(textStart, timesSlower, textEnd);
        textFlow.setPrefWidth(width);
        textFlow.setTextAlignment(CENTER);
        textFlow.setPadding(new Insets(7, 10, 7, 10));
       // textFlow.setFont
        this.textFlow = textFlow;

        this.getChildren().addAll(backgroundRect, textFlow);



        return textFlow;

    }*/

    public void setTimesSlower(double timesSlower) {
        BigDecimal bd = new BigDecimal(timesSlower).setScale(1, RoundingMode.HALF_UP);
        double newInput = bd.doubleValue();
        this.timesSlower = newInput;
    }

}
