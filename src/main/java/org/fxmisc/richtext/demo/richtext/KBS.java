package org.fxmisc.richtext.demo.richtext;

import com.fxexperience.javafx.animation.BounceOutRightTransition;
import com.fxexperience.javafx.animation.FadeInUpTransition;
import com.fxexperience.javafx.animation.ShakeTransition;
import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;


public class KBS extends HBox implements UIColors {
    private boolean isHidden = true;
    private boolean isPinned = false;// todo, general thing: place properties above the function the relate to. if it's used a lot of places, i would place it up here
    protected int kbsTimesUsedTotal = 0;
    protected int kbsTimesUsedInstance = 0;
    String functionality;
    protected int tbTimesClickedTotal = 0;
    protected int tbTimesClickedInstance = 0; //times clicked since last time shortcut were used
    private int nrOnList;
    private boolean pinned;
    public int[] KBStype = new int[5];
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); //Allows access for the logger

    private double opacity = 1;
    Rectangle backgroundRect, colorRect;
    ImageView icon;
    Text shortcut;
    Pane kbsPane = new Pane();

    SkillOMeter convinceOMeter;
    SkillOMeter rewardOMeter;

    HBox content;


    public boolean isShown = false;


    KBS(String shortcut, String functionality, String iconPath) {


//        Color grColorRed1 = new Color(0.5, 0, 0, 0.70);
//        Color grColorRed2 = new Color(0.5, 0, 0, 0.30);

        // initial rectangle
        this.setUserData(functionality);
        this.setId(functionality);
        this.functionality = functionality;
        icon = new ImageView(new Image(iconPath, 40, 40, true, true));
        backgroundRect = new Rectangle(170, 50, Color.LIGHTGREY);
        backgroundRect.setStroke(borderColor);
        backgroundRect.setOpacity(0.8);


        colorRect = new Rectangle(170, 50, UIColors.setKBSAlertColor());
        colorRect.setStroke(borderColor);

        this.content = new HBox(5);
//        this.setStyle("-fx-border-color: black");
        this.setAlignment(Pos.CENTER_RIGHT);
        this.content.setPadding(new Insets(5, 5, 5, 5));
        //TODO make text in ctrl+shift+sth fit into box
        this.shortcut = new Text(shortcut);
        this.shortcut.setFont(new Font(30));

        content.getChildren().addAll(this.icon, this.shortcut);

        this.backgroundRect.setFill(UIColors.setKBSColor());

        kbsPane.getChildren().addAll(backgroundRect, colorRect, content);

        this.setSpacing(5);

        Text textStartConvince = new Text("When not using shortcuts, \n you are ");
        Text textEndConvince = new Text(" times slower!");
        convinceOMeter = new SkillOMeter(textStartConvince, textEndConvince, textAlertColor);

        Text textStartReward = new Text("When using the " + functionality + " shortcut, \n you are ");
        Text textEndReward = new Text(" times faster!");
        rewardOMeter = new SkillOMeter(textStartReward, textEndReward, textApprovalColor);

        this.getChildren().addAll(convinceOMeter, kbsPane);
        //.fade(0.1,2).play();
        this.setOnMouseMoved(event -> {
        });
        this.setVisible(false);
        this.setManaged(false);
    }

    public void shortcutUsed() {
        this.kbsTimesUsedTotal++;
        this.kbsTimesUsedInstance++;
        rewardOMeter.setKbsTimesUsedInstance(this.kbsTimesUsedInstance);

        this.tbTimesClickedInstance = 0;

        String kbsLog = Integer.toString(kbsTimesUsedTotal);
        LOGGER.info(functionality + " KBS executed " + kbsLog); //Logs what KBS was used and the amount.

        if (!this.isHidden && !this.isPinned) {
            this.hide();
            this.isHidden = true;
        }

        rewardOMeter.manageRewardOMeter(0.2, 8);

    }

    public void toolbarPressed() {
        this.tbTimesClickedTotal++;
        this.tbTimesClickedInstance++;
        convinceOMeter.tbTimesClickedInstance(this.tbTimesClickedInstance);

        this.kbsTimesUsedInstance = 0;
        String tbLog = Integer.toString(tbTimesClickedTotal);
        LOGGER.info(functionality + " Toolbar clicked " + tbLog); //Logs what toolbar was clicked and the amount
        LOGGER.info(this.functionality + " clicked " + this.tbTimesClickedInstance + " times since last time shortcut were used"); //Logs amount until KBS used.
        if (this.isHidden) {
            this.show();
            this.isHidden = false;
        } else {
//            this.seekAttention();
        }
        convinceOMeter.manageConvinceOMeter(1.5, 8);
    }

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

        System.out.println("Hi there! Now I'm hidden!");
        BounceOutRightTransition Anim = new BounceOutRightTransition(this);
        Anim.setOnFinished(event -> {
            setVisible(false);
            setManaged(false);
//            resetGrow(); // reset size + translation
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

    public void hoverShake() {
        ShakeTransition shakeTransition = new ShakeTransition(kbsPane);
        shakeTransition.setRate(5);
        shakeTransition.setInterpolator(Interpolator.EASE_OUT);
        shakeTransition.play();
        this.colorRect.setOpacity(1.);
    }

//    public void seekAttention() {
//        ShakeTransition shakeTransition = new ShakeTransition(kbsPane);
//        shakeTransition.setRate(5);
//        shakeTransition.setInterpolator(Interpolator.EASE_OUT);
//        shakeTransition.play();
//    }

    boolean attentionable = false;

    void initialClickGate() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                attentionable = true;
//                System.out.println("attentionable:" + attentionable);
            }
        }, 2000);
    }
}



