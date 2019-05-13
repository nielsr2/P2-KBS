//package org.fxmisc.richtext.demo.richtext;
//
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//import javafx.scene.text.TextFlow;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//
//import static javafx.scene.paint.Color.DARKGRAY;
//import static javafx.scene.text.TextAlignment.CENTER;
//
//public class RewardOMeter extends StackPane implements UIColors {
//
//
//    Rectangle backgroundRect;
//
//    int width = 170;
//    int height = 49;
//    Text textStart = new Text("When using shortcuts, \n you are ");
//    Text textEnd = new Text(" faster!");
//    TextFlow textFlow;
//    private double timesFaster;
//    private Text timesFasterText = new Text();
//    private String timesFasterString;
//
//    public void setKbsTimesUsedInstance(int kbsTimesUsedInstance) {
//        this.kbsTimesUsedInstance = kbsTimesUsedInstance;
//    }
//
//    private int kbsTimesUsedInstance;
//
//    RewardOMeter() {
//
//        this.setVisible(false);
//        this.setManaged(false);
//        this.setAlignment(Pos.CENTER);
//
//        textFlow = new TextFlow(textStart, timesFasterText, textEnd);
//
//        textFlow.setPrefWidth(width);
//        textFlow.setTextAlignment(CENTER);
//        textFlow.setPadding(new Insets(7, 10, 7, 10));
//
//        //System.out.println(getFont)
//
//        textStart.setFont(Font.font("Sergoe UI", 12));
//        timesFasterText.setFont(Font.font("Sergoe UI", FontWeight.BOLD, 12));
//        timesFasterText.setFill(textApprovalColor);
//        textEnd.setFont(Font.font("Sergoe UI", 12));
//
//
//        backgroundRect = new Rectangle(width, height, Color.LIGHTGREY);
//        backgroundRect.setArcHeight(100);
//        backgroundRect.setArcWidth(40);
//        backgroundRect.setStroke(DARKGRAY);
//
//        this.getChildren().addAll(backgroundRect, textFlow);
//    }
//
//    public void showText() {
//        timesFasterString = Double.toString(this.timesFaster);
//        timesFasterText.setText(timesFasterString);
//    }
//
//    public double getTimesFaster() {
//        return this.timesFaster;
//    }
//
//    public void setTimesFaster(double timesFaster) {
//        BigDecimal bd = new BigDecimal(timesFaster).setScale(1, RoundingMode.HALF_UP);
//        double newInput = bd.doubleValue();
//        this.timesFaster = newInput;
//    }
//
//    public void manageRewardOMeter() {
//        int upperThreshold = 8;
//        double lowerThreshold = 0.5;
//        if (kbsTimesUsedInstance > 1 && timesFaster < upperThreshold && timesFaster > lowerThreshold) {
//            this.setVisible(true);
//            this.setManaged(true);
//            this.showText();
//        } else {
//            this.setVisible(false);
//            this.setManaged(false);
//        }
//    }
//}
