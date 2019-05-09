package org.fxmisc.richtext.demo.richtext;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConvinceOMeter extends Pane {

    Rectangle backgroundRect;

    private double timesSlower;
    String argument;
    private Text text = new Text(argument);



    ConvinceOMeter(float timesSlower){
      this.setVisible(false);
      this.setManaged(false);




        this.timesSlower = timesSlower;
        backgroundRect = new Rectangle(170, 50, Color.LIGHTGREY);

        this.showText1();
        TextFlow textFlow = new TextFlow(text);


        this.getChildren().addAll(backgroundRect, textFlow);



    }

    public Text showText1(){


        text.setWrappingWidth(50);

        text.setText( "You are " + this.timesSlower + " times slower by not using this shortcut");
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
