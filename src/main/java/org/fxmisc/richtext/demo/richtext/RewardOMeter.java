package org.fxmisc.richtext.demo.richtext;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class RewardOMeter extends Pane {

    private double timesFaster;
    Rectangle backgroundRect;
    private Text text = new Text();



    public void setKbsTimesUsedInstance(int kbsTimesUsedInstance) {
        this.kbsTimesUsedInstance = kbsTimesUsedInstance;
    }

    private int kbsTimesUsedInstance;

    RewardOMeter(){

        this.setVisible(false);
        this.setManaged(false);

        //this.timesFaster = timesFaster;
        backgroundRect = new Rectangle(170, 50, Color.LIGHTGREY);

        TextFlow textFlow = new TextFlow(text);

        this.getChildren().addAll(backgroundRect,textFlow);
    }

    public void manageRewardOMeter() {
        System.out.println(kbsTimesUsedInstance);
        if (kbsTimesUsedInstance == 2) {
            this.setVisible(true);
            this.setManaged(true);
            this.showText1();
        } else if (kbsTimesUsedInstance == 5) {
            this.setVisible(true);
            this.setManaged(true);
            this.showText2();
        }
        else {
            this.setVisible(false);
            this.setManaged(false);
        }
    }

    public Text showText1() {
        text.setText("You are " + this.timesFaster + " times slower by not using this shortcut");
        return text;
    }

    public Text showText2(){
        text.setText( "testing 123");
        return text;
    }

    public void setTimesFaster(double timesFaster) {
        this.timesFaster = timesFaster;
    }

    public double getTimesFaster() {
        return this.timesFaster;
    }

}
