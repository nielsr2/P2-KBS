package org.fxmisc.richtext.demo.richtext;


import com.fxexperience.javafx.animation.BounceOutRightTransition;
import com.fxexperience.javafx.animation.FadeInUpTransition;
import com.fxexperience.javafx.animation.ShakeTransition;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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


public class KBS extends HBox {
    private String oprSystem;
    private boolean isHidden = true;
    private boolean isPinned = false;
    protected int kbsTimesUsed = 0;
    String functionality;
    protected int tbTimesClickedTotal = 0;
    protected int tbTimesClickedInstance = 0; //times clicked since last time shortcut were used
    private int nrOnList;
    private boolean pinned;
    public int KBStype[] = new int[5];

    private double opacity = 1;
    Rectangle backgroundRect;
    ImageView icon;
    Text shortcut;
    Pane kbsPane = new Pane();
    ConvinceOMeter convinceOMeter = new ConvinceOMeter(2);
    HBox content;

    KBS(String shortcut, String functionality, String iconPath) {
        this.setVisible(false);
        this.setManaged(false);


        // initial rectangle
        this.setUserData(functionality);
        this.setId(functionality);
        this.functionality = functionality;
        icon = new ImageView(new Image(iconPath, 40, 40 ,true, true));
        backgroundRect = new Rectangle(170, 50, Color.LIGHTGREY);

        this.content = new HBox(5);
        this.setStyle("-fx-border-color: black");
        this.setAlignment(Pos.CENTER_RIGHT);
        this.content.setPadding(new Insets(5, 5, 5, 5));
        //TODO make text in ctrl+shift+sth fit into box
        this.shortcut = new Text(shortcut);
        this.shortcut.setFont(new Font(30));

        content.getChildren().addAll(this.icon, this.shortcut);


        kbsPane.getChildren().addAll(backgroundRect, content);

        this.setSpacing(5);


        this.getChildren().addAll(convinceOMeter,kbsPane);
        //.fade(0.1,2).play();
        this.setOnMouseMoved(event -> {
            System.out.println(oprSystem);
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

    public void shortcutUsed() {
        this.kbsTimesUsed++;
        this.tbTimesClickedInstance = 0;

        System.out.println(this.functionality + " KBS used : " + this.kbsTimesUsed );
        if(this.isHidden == false && this.isPinned == false) {
            this.hide();
            this.isHidden = true;
        }

    }
    public void toolbarPressed() {
        this.tbTimesClickedTotal++;
        this.tbTimesClickedInstance++;
        System.out.println(this.functionality + " toolbar used : " + this.tbTimesClickedTotal );
        System.out.println(this.functionality + " toolbar used : " + this.tbTimesClickedInstance );
        if(this.isHidden == true) {
            this.show();
            this.isHidden = false;
        } else {
            this.seekAttention();
        }
        this.manageConvinceOMeter();
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

    public void show(){
        KBS k = this;
        FadeInUpTransition Anim = new FadeInUpTransition(k);
        Anim.play();
        this.setVisible(true);
        this.setManaged(true);
    }

    public void hide() {
        KBS k = this;
        System.out.println("Hi there! Now I'm hidden!");
        BounceOutRightTransition Anim = new BounceOutRightTransition(k);
        Anim.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                k.setVisible(false);
                k.setManaged(false);
            }
        });
        Anim.play();
    }

    public void pin(boolean isPinned) {
        this.isPinned = isPinned;
        if (isPinned == true) {
            System.out.println("Hi there! Now I'm pinned");
        } else {
            System.out.println("Hi there! Now I'm unpinned");
        }
    }

    public void forget() {
    }

    public void seekAttention(){
        KBS k = this;
        System.out.println("C'mon! You stupid!");
        ShakeTransition Anim = new ShakeTransition(k);
        Anim.play();
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

    public void manageConvinceOMeter(){
        if(tbTimesClickedInstance == 2){
            convinceOMeter.setVisible(true);
            convinceOMeter.setManaged(true);
            convinceOMeter.showText1();
        }

        else if(tbTimesClickedInstance == 5){
            convinceOMeter.setVisible(true);
            convinceOMeter.setManaged(true);
            convinceOMeter.showText2();
        }
        else{
            convinceOMeter.setVisible(false);
            convinceOMeter.setManaged(false);
        }
    }




}
