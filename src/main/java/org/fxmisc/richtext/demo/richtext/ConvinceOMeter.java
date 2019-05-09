package org.fxmisc.richtext.demo.richtext;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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

       /* HBox content = new HBox(5);
        content.setPadding(new Insets(5, 5, 5, 5));



        content.getChildren().addAll(icon, this.shortcut);*/


//       this.text("You are " + this.timesSlower + " times slower by not using this shortcut" );
        this.showText1();
        TextFlow textFlow = new TextFlow(text);

        this.getChildren().addAll(backgroundRect, textFlow);



    }

    public Text showText1(){
        text.setText( "You are " + this.timesSlower + " times slower by not using this shortcut");
        return text;

    }

    public Text showText2(){
        text.setText( "testing 123");
        return text;

    }

    public void setTimesSlower(double timesSlower) {
        this.timesSlower = timesSlower;
    }

}
