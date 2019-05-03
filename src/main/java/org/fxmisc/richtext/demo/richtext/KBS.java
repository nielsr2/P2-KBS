package org.fxmisc.richtext.demo.richtext;


import com.fxexperience.javafx.animation.FadeInDownBigTransition;
import com.fxexperience.javafx.animation.TadaTransition;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;



public class KBS extends Pane {
    private String oprSystem;
    private boolean visible = false;
    private boolean managed = false;
    protected int kbsTimesUsed = 0;
    String functionality;
    protected int tbTimesClicked = 0;
    private int nrOnList;
    private boolean pinned;
    public int KBStype[] = new int[5];

    private double opacity = 1;
    Rectangle backgroundRect;
    ImageView icon;
    Text shortcut;

    KBS() {
        this.setVisible(false);
        this.setManaged(false);
    }
    KBS(String shortcut, String iconPath) {

        // initial rectangle
        backgroundRect = new Rectangle(170, 50, Color.LIGHTGREY);

        HBox content = new HBox(5);
        content.setPadding(new Insets(5, 5, 5, 5));
        icon = new ImageView(new Image(iconPath));
        icon.setCache(true);
        this.shortcut = new Text(shortcut);
        this.shortcut.setFont(new Font(30));

        content.getChildren().addAll(icon, this.shortcut);


        this.getChildren().addAll(backgroundRect, content);
        //.fade(0.1,2).play();

    }
    HBox content;
    KBS(String shortcut, String functionality, String iconPath) {
        this();
        // initial rectangle
        this.setUserData(functionality);
        this.setId(functionality);
        this.functionality = functionality;
        icon = new ImageView(new Image(iconPath, 40, 40 ,true, true)); // TODO SOMEBODY SET A BORDER ON THE ICON
        backgroundRect = new Rectangle(170, 50, Color.LIGHTGREY);

        this.content = new HBox(5);
        this.content.setPadding(new Insets(5, 5, 5, 5));

        this.shortcut = new Text(shortcut);
        this.shortcut.setFont(new Font(30));

        content.getChildren().addAll(this.icon, this.shortcut);


        this.getChildren().addAll(backgroundRect, content);
        //.fade(0.1,2).play();
        this.setOnMouseMoved(event -> {
            System.out.println("test");
        });
    }
    /**
     * colors for the gradient
     */
    // http://www.java2s.com/Tutorials/Java/JavaFX/0110__JavaFX_Gradient_Color.htm

    Color grColorGrey1 = new Color(0.5, 0.5, 0.5, 0.30);
    Color grColorGrey2 = new Color(0.7, 0.7, 0.7, 0.15);
    Color grColorRed1 = new Color(0.5, 0, 0, 0.70);
    Color grColorRed2 = new Color(0.5, 0, 0, 0.30);
    Color grColorYellow1 = new Color(0.9, 0.7, 0, 0.70);
    Color grColorYellow2 = new Color(0.9, 0.7, 0, 0.30);
    Color grColorGreen1 = new Color(0.2, 0.6, 0, 0.70);
    Color grColorGreen2 = new Color(0.2, 0.6, 0, 0.30);
// TODO CAN WE MAYBE JUST CALL THE FUNCTION USE FOR THIS PARAM, INSIDE KBSMANAGER
//    KBS(String oprSystem) {
//        this.oprSystem = oprSystem;
//    }

    public void shortcutUsed() {
        this.kbsTimesUsed++;

        System.out.println(this.functionality + " KBS used : " + this.kbsTimesUsed );
    }
    public void toolbarPressed() {
        this.tbTimesClicked++;
        System.out.println(this.functionality + " toolbar used : " + this.tbTimesClicked );
    }

    /**
     * methods for each gradient color gradient
     */

    //Function for setting color and setting linear gradient
    public void setColor(Color colorLeft, Color colorRight) {
        Stop[] stopsColor = new Stop[]{new Stop(0, colorRight), new Stop(1, colorLeft)};
        LinearGradient lgColor = new LinearGradient(1, 0, 0, 0, true, CycleMethod.NO_CYCLE, stopsColor);

        this.backgroundRect.setFill(lgColor);
    }

    //Function for setting color with linear gradient and setting opacity
    public void setColor(Color color, double opacity) {
        Color colorLeft = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity/2);
        Color colorRight = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);

        Stop[] stopsColor = new Stop[]{new Stop(0, colorRight), new Stop(1, colorLeft)};
        LinearGradient lgColor = new LinearGradient(1, 0, 0, 0, true, CycleMethod.NO_CYCLE, stopsColor);

        this.backgroundRect.setFill(lgColor);
    }

    void show(boolean show){
        this.setVisible(show);
        this.setManaged(show);
    }

    void anim() {               // TODO, Kristitinn will, so hey this is an animation first triggers an animation after this KBS has been clicked, and another animation following the end of the first
        // https://github.com/fxexperience/code/tree/master/FXExperienceControls/src/com/fxexperience/javafx/animation
        // these are the animations we use. look at them and get creative applying them, or follow their design to make own animations
        System.out.println("Animation triggered!");
        KBS k = this;
        this.setOnMousePressed((e -> {
            System.out.println("Hi there! You clicked me!");
            FadeInDownBigTransition Anim = new FadeInDownBigTransition(k);
            Anim.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    new TadaTransition(k).play();
                }
            });
            Anim.play();
        }));
//
    }
    public FadeTransition fade(double opacityEnd, double time) {

        double opacityStart = this.opacity;


        FadeTransition fade = new FadeTransition(Duration.seconds(time), this);
        fade.setFromValue(opacityStart);
        fade.setToValue(opacityEnd);
        //fade.setCycleCount(Timeline.INDEFINITE);
        //fade.setAutoReverse(true);
        fade.play(); //start animation

        return fade;

        //this.setOnMousePressed(e -> System.out.println("adasfdf"));


    }


}
