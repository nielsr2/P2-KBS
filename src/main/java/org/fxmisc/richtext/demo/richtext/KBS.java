package org.fxmisc.richtext.demo.richtext;

import com.fxexperience.javafx.animation.BounceOutRightTransition;
import com.fxexperience.javafx.animation.FadeInUpTransition;
import com.fxexperience.javafx.animation.ShakeTransition;
import com.fxexperience.javafx.animation.SwingTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import java.awt.*;


public class KBS extends HBox {
    private boolean isHidden = true;
    private boolean isPinned = false;// todo, general thing: place properties above the function the relate to. if it's used a lot of places, i would place it up here
    protected int kbsTimesUsedTotal = 0;

    public void setKbsTimesUsedInstance(int kbsTimesUsedInstance) {
        this.kbsTimesUsedInstance = kbsTimesUsedInstance;
    }

    protected int kbsTimesUsedInstance = 0;
    String functionality;
    protected int tbTimesClickedTotal = 0;
    protected int tbTimesClickedInstance = 0; //times clicked since last time shortcut were used
    private int nrOnList;
    private boolean pinned;
    public int KBStype[] = new int[5];
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); //Allows access for the logger

    private double opacity = 1;
    Rectangle backgroundRect, colorRect;
    ImageView icon;
    Text shortcut;
    Pane kbsPane = new Pane();
    ConvinceOMeter convinceOMeter = new ConvinceOMeter();
    RewardOMeter rewardOMeter = new RewardOMeter();
    HBox content;

    KBS(String shortcut, String functionality, String iconPath) {
        this.setVisible(false);
        this.setManaged(false);

//        Color grColorRed1 = new Color(0.5, 0, 0, 0.70);
//        Color grColorRed2 = new Color(0.5, 0, 0, 0.30);

        // initial rectangle
        this.setUserData(functionality);
        this.setId(functionality);
        this.functionality = functionality;
        icon = new ImageView(new Image(iconPath, 40, 40, true, true));
        backgroundRect = new Rectangle(170, 50, Color.LIGHTGREY);
        colorRect = new Rectangle(170, 50, Color.RED);
//        this.setColor(grColorRed1,1);

//        SVGPath svgPin = new SVGPath();
//        SVGPath svgClose = new SVGPath();
//        String path = "M6.78,1.96c0,0-1.23-2.25-0.35-1.77c1.29,0.69,2.79,1.7,3.66,2.34c0.86,0.63-1.55,0.32-1.55,0.32L6.82,5.79 c0,0,1.04,3.12,0.59,3.33c-0.53,0.25-2.3-1-2.68-1.3C4.67,7.76,4.57,7.78,4.52,7.85c0,0-2.59,3.51-2.73,3.37 c-0.14-0.14,2.1-3.77,2.1-3.77C3.93,7.39,3.91,7.3,3.84,7.26C3.53,7.09,1.83,6,1.79,5.31C1.75,4.57,5.02,4.56,5.02,4.56L6.78,1.96z";
//        svgPin.setContent(path);
//
//        String pathClose = "M5.66,4.78 9.6,0.85 10.52,1.77 6.57,5.7 10.52,9.64 9.61,10.53 5.68,6.6 1.74,10.53 0.82,9.61 4.78,5.67 0.84,1.71 1.74,0.81z";
//        //Setting the SVGPath in the form of string
//        svgClose.setContent(pathClose);

        this.content = new HBox(5);
        this.setStyle("-fx-border-color: black");
        this.setAlignment(Pos.CENTER_RIGHT);
        this.content.setPadding(new Insets(5, 5, 5, 5));
        //TODO make text in ctrl+shift+sth fit into box
        this.shortcut = new Text(shortcut);
        this.shortcut.setFont(new Font(30));

        content.getChildren().addAll(this.icon, this.shortcut);


        kbsPane.getChildren().addAll(backgroundRect, colorRect, content);

        this.setSpacing(5);


        this.getChildren().addAll(convinceOMeter, kbsPane);
        //.fade(0.1,2).play();
        this.setOnMouseMoved(event -> {
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
        this.kbsTimesUsedTotal++;
        this.kbsTimesUsedInstance++;
        rewardOMeter.setKbsTimesUsedInstance(this.kbsTimesUsedInstance);
        rewardOMeter.manageRewardOMeter();

        this.tbTimesClickedInstance = 0;

        String kbsLog = Integer.toString(kbsTimesUsedTotal);
        LOGGER.info(functionality + " KBS executed " + kbsLog); //Logs what KBS was used and the amount.

        if (this.isHidden == false && this.isPinned == false) {
            this.hide();
            this.isHidden = true;
        }

    }

    public void toolbarPressed() {
        this.tbTimesClickedTotal++;
        this.tbTimesClickedInstance++;
        this.kbsTimesUsedInstance = 0;
        String tbLog = Integer.toString(tbTimesClickedTotal);
        LOGGER.info(functionality + " Toolbar clicked " + tbLog); //Logs what toolbar was clicked and the amount
        LOGGER.info(this.functionality + " clicked " + this.tbTimesClickedInstance + " times since last time shortcut were used"); //Logs amount until KBS used.
        if (this.isHidden == true) {
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
        Color colorLeft = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity / 2);
        Color colorRight = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);

        Stop[] stopsColor = new Stop[]{new Stop(0, colorRight), new Stop(1, colorLeft)};
        LinearGradient lgColor = new LinearGradient(1, 0, 0, 0, true, CycleMethod.NO_CYCLE, stopsColor);

        this.backgroundRect.setFill(lgColor);
    }

    public boolean isShown = false;
    boolean attentionLock = true;

    public void show() {
        isShown = true;
        FadeInUpTransition Anim = new FadeInUpTransition(this);
        Anim.play();
        this.setVisible(true);
        this.setManaged(true);
        this.initialClickGate();
    }

    public void hide() {
        isShown = false;
        this.attentionable = false;
        KBS k = this;
        System.out.println("Hi there! Now I'm hidden!");
        BounceOutRightTransition Anim = new BounceOutRightTransition(k);
        Anim.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                k.setVisible(false);
                k.setManaged(false);
                k.resetGrow(); // reset size + translation
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

    public void seekAttention() {
        KBS k = this;
        System.out.println("C'mon! You stupid!");
        ShakeTransition Anim = new ShakeTransition(k);
//        Anim.play();
        grow(1.5, .8);
    }

    void resetGrow() {
        this.setScaleX(1);
        this.setScaleY(1);
        this.setTranslateX(0.);
        this.setTranslateY(0.);
    }

    void grow(double size, double seconds) {
        System.out.println("grow ran!!!!!");
        TranslateTransition tt = new TranslateTransition(Duration.seconds(seconds), this);
        tt.setToX((-1 * this.getHeight() * 2) * (size - 1));
        tt.setToY((-1 * this.getWidth() / 4) * (size - 1));
        ScaleTransition st = new ScaleTransition(Duration.seconds(seconds), this);
        st.setToX(size);
        st.setToY(size);
        ParallelTransition pt = new ParallelTransition(tt, st);
        pt.play();
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

    public void manageConvinceOMeter() {
        int upperThreshold = 8;
        double lowerThreshold = 1.5;
        if (tbTimesClickedInstance > 1 && convinceOMeter.getTimesSlower() < upperThreshold && convinceOMeter.getTimesSlower() > lowerThreshold) {
            convinceOMeter.setVisible(true);
            convinceOMeter.setManaged(true);
            convinceOMeter.showText1();
        } else if (tbTimesClickedInstance == 5) {
            convinceOMeter.setVisible(true);
            convinceOMeter.setManaged(true);
            convinceOMeter.showText2();
        } else {
            convinceOMeter.setVisible(false);
            convinceOMeter.setManaged(false);
        }
    }

    double buttonX;
    double buttonY;
    double buttonWidth;

    public void setButtonCoordinates(double buttonX, double buttonY, double buttonWidth) {
        System.out.println(this.functionality + " x:" + buttonX + " y: " + buttonY);
        this.buttonX = buttonX + buttonWidth / 2;
        this.buttonY = buttonY + buttonWidth / 2;
        this.buttonWidth = buttonWidth;
    }

    boolean didit, dothatcolorthing;

    public void dontdoit() {
        SwingTransition pt = new SwingTransition(this);
        pt.play();
        this.colorRect.setOpacity(1.);
    }

    boolean attentionable = false;

    void initialClickGate() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                attentionable = true;
                System.out.println("attentionable:" + attentionable);
            }
        }, 4000);
    }
}



