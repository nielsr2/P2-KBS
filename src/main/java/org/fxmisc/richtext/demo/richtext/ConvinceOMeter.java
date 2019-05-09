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
    String argument;
    private Text text = new Text(argument);



    ConvinceOMeter(float timesSlower){


        this.setVisible(false);
      this.setManaged(false);
        this.setAlignment(Pos.CENTER);





        this.timesSlower = timesSlower;
        backgroundRect = new Rectangle(width, height, Color.LIGHTGREY);
        backgroundRect.setArcHeight(100);
        backgroundRect.setArcWidth(40);
        backgroundRect.setStroke(DARKGRAY);


        this.showText1();


        TextFlow textFlow = new TextFlow(text);

        textFlow.setPrefWidth(width);
        textFlow.setTextAlignment(CENTER);
        textFlow.setPadding(new Insets(7, 10, 7, 10));

        this.getChildren().addAll(backgroundRect, textFlow);



    }

    public Text showText1(){
        text.setWrappingWidth(width);
        String string = Double.toString(this.timesSlower);
        Text timesSlower = new Text(string);

        text.setText("You are " + string + " times slower by \n not using this shortcut");
        return text;

    }

    public Text showText2(){
        text.setText( "testing 123");
        return text;

    }

    public void setTimesSlower(double timesSlower) {
        BigDecimal bd = new BigDecimal(timesSlower).setScale(1, RoundingMode.HALF_UP);
        double newInput = bd.doubleValue();
        this.timesSlower = newInput;
    }

}
