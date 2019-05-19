package org.p2;

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

import static org.p2.UIColors.*;


public class KBS extends HBox {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); //Allows access for the logger
    public boolean isShown = false;
    //
    //                         _
    //    ___ ___  _   _ _ __ | |_ ___
    //   / __/ _ \| | | | '_ \| __/ __|
    //  | (__ (_) | |_| | | | | |_\__ \
    //   \___\___/ \__,_|_| |_|\__|___/
    //
    protected int kbsTimesUsedTotal = 0;
    protected int kbsTimesUsedInstance = 0;
    protected int tbTimesClickedTotal = 0;
    protected int tbTimesClickedInstance = 0; //times clicked since last time shortcut were used
    public Notifications notifications;
    Text textStartConvince = new Text("When not using shortcuts, \n you are "); // this might be dumb way too do it
    Text textEndConvince = new Text(" times slower!");
    public Notifications notification;


    //                       _                   _
    //    ___ ___  _ __  ___| |_ _ __ _   _  ___| |_ ___  _ __
    //   / __/ _ \| '_ \/ __| __| '__| | | |/ __| __/ _ \| '__|
    //  | (__ (_) | | | \__ \ |_| |  | |_| | (__| |_ (_) | |
    //   \___\___/|_| |_|___/\__|_|   \__,_|\___|\__\___/|_|
    //
    public Rectangle backgroundRect, colorRect;
    ImageView icon;
    Text textStartReward = new Text("When using the \n");
    Pane kbsPane = new Pane();
    String functionality;
    Text textMiddleReward = new Text(" shortcut, \n you are ");
    private boolean isPinned = false;

    Text textEndReward = new Text(" times faster!");
    Text shortcutText;
    HBox content;


    KBS(String shortcutText, String functionality, String iconPath) {

        notifications = new Notifications(textStartConvince, textEndConvince, textAlertColor);
        notification = new Notifications(textStartReward, textMiddleReward, textEndReward, new Text(functionality), textApprovalColor);

        this.setUserData(functionality);
        this.setId(functionality);
        this.functionality = functionality; // consider deletion, not really needed?

        icon = new ImageView(new Image(iconPath, 40, 40, true, true));

        // BACKGROUND RECTANGLE
        backgroundRect = new Rectangle(170, 50, Color.LIGHTGREY);
        backgroundRect.setStroke(borderColor);
        backgroundRect.setOpacity(0.8);
        this.backgroundRect.setFill(UIColors.setKBSColor());

        // COLOR OVERLAY
        colorRect = new Rectangle(170, 50, UIColors.setKBSAlertColor());
        colorRect.setStroke(borderColor);

        // STYLING
        this.content = new HBox(5);
//        this.setStyle("-fx-border-color: black");
        this.setAlignment(Pos.CENTER_RIGHT);
        this.content.setPadding(new Insets(5));
        this.setSpacing(5);

        // TEXT
        this.shortcutText = new Text(shortcutText);
        this.shortcutText.setFont(new Font(30));

        // ADD
        content.getChildren().addAll(this.icon, this.shortcutText);
        kbsPane.getChildren().addAll(backgroundRect, colorRect, content);
        this.getChildren().addAll(notifications, kbsPane);

//        this.setOnMouseClicked(event -> {     // i dont understand the point of dis?
//            this.ACTIVATED = false;
//            System.out.println(ACTIVATED);
//        });
//        this.setOnKeyPressed(event -> {
//            this.ACTIVATED = true;
//            System.out.println(ACTIVATED);
//        });
        this.setVisible(false);
        this.setManaged(false);
    }

    //   _           _   _
//  | |__  _   _| |_| |_ ___  _ __
//  | '_ \| | | | __| __/ _ \| '_ \
//  | |_) | |_| | |_| |_ (_) | | | |
//  |_.__/ \__,_|\__|\__\___/|_| |_|
//
    double buttonX;
    double buttonY;
    double buttonWidth;


    public void setButtonWidth(double buttonWidth) {
        this.buttonWidth = buttonWidth;
    }

    public void shortcutUsed() {
        if (ACTIVATED) {
            this.kbsTimesUsedTotal++;
            this.kbsTimesUsedInstance++;
            notification.setKbsTimesUsedInstance(this.kbsTimesUsedInstance);

            this.tbTimesClickedInstance = 0;

            String kbsLog = Integer.toString(kbsTimesUsedTotal);
            LOGGER.info(functionality + " KBS executed " + kbsLog); //Logs what KBS was used and the amount.

            if (!this.isHidden && !this.isPinned) {
                this.hide();
                this.isHidden = true;
            }
            notification.manageRewardOMeter(0.05, 8);
        }
    }


    //         _     _ _     _ _ _ _
    //  __   ___)___(_) |__ (_) (_) |_ _   _
    //  \ \ / / / __| | '_ \| | | | __| | | |
    //   \ V /| \__ \ | |_) | | | | |_| |_| |
    //    \_/ |_|___/_|_.__/|_|_|_|\__|\__, |
    //                                 |___/

    boolean hovered;
    private boolean isHidden = true;

    public void toolbarPressed() {
        if (ACTIVATED) {
            this.tbTimesClickedTotal++;
            this.tbTimesClickedInstance++;
            notifications.tbTimesClickedInstance(this.tbTimesClickedInstance);

            this.kbsTimesUsedInstance = 0;
            notification.rewardShown = false;
            String tbLog = Integer.toString(tbTimesClickedTotal);
            LOGGER.info(functionality + " Toolbar clicked " + tbLog); //Logs what toolbar was clicked and the amount
            LOGGER.info(this.functionality + " clicked " + this.tbTimesClickedInstance + " times since last time shortcut were used"); //Logs amount until KBS used.
            if (this.isHidden) {
                this.show();
                this.isHidden = false;
            } else {
//            this.seekAttention();
            }
            notifications.manageNotifications(1.5, 8);
        }
    }
    //              _   _            _   _
    //    __ _  ___| |_(_)_   ____ _| |_(_) ___  _ __
    //   / _` |/ __| __| \ \ / / _` | __| |/ _ \| '_ \
    //  | (_| | (__| |_| |\ V / (_| | |_| | (_) | | | |
    //   \__,_|\___|\__|_| \_/ \__,_|\__|_|\___/|_| |_|
    //
    private boolean ACTIVATED = true; // BOOLEAN USED FOR TOGGLING OUR SYSTEM

    public void show() {
        isShown = true;
        FadeInUpTransition Anim = new FadeInUpTransition(this);
        Anim.play();
        this.setVisible(true);
        this.setManaged(true);
        this.initialClickGate();
    }

    boolean canAnimate = false;

    public void setButtonCoordinates(double buttonX, double buttonY, double buttonWidth) {
//        System.out.println(this.functionality + " x:" + buttonX + " y: " + buttonY);
        this.buttonX = buttonX + buttonWidth / 2;
        this.buttonY = buttonY + buttonWidth / 2;
        this.buttonWidth = buttonWidth;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void hoverShake() {
        ShakeTransition shakeTransition = new ShakeTransition(kbsPane);
        shakeTransition.setRate(5);
        shakeTransition.setInterpolator(Interpolator.EASE_OUT);
        shakeTransition.play();
        this.colorRect.setOpacity(1.);
    }

    public void hide() {
        isShown = false;
        this.canAnimate = false;

        System.out.println("Hi there! Now I'm hidden!");
        BounceOutRightTransition Anim = new BounceOutRightTransition(this);
        Anim.setOnFinished(event -> {
            setVisible(false);
            setManaged(false);
//            resetGrow(); // reset size + translation
        });
        Anim.play();
    }

    void initialClickGate() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                canAnimate = true;
//                System.out.println("canAnimate:" + canAnimate);
            }
        }, 2000);
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public boolean isACTIVATED() {
        return ACTIVATED;
    }

    void toggleActivation() {
        this.ACTIVATED = !this.ACTIVATED;
    }


//    public void pin(boolean isPinned) {
//        this.isPinned = isPinned;
//        if (isPinned == true) {
//            System.out.println("Hi there! Now I'm pinned");
//        } else {
//            System.out.println("Hi there! Now I'm unpinned");
//        }
//    }

}



