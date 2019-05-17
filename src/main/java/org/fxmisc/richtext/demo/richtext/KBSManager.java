package org.fxmisc.richtext.demo.richtext;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.awt.*;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import static javafx.geometry.Pos.BOTTOM_RIGHT;

// TODO add 'previously used shortcuts' that appears when some thing in here is hovered?
// TODO make a log of session? (KBSused,ToolbarPressed etc.) probably want a timelime + sum up statistic
// TODO *GENERAL THING*Can we say "you'll be when working 500% more effienct" percentage efficiency ( if we think only of 'actions performed' when using software, then KBS are clearly many %'s faster.
// TODO MAKE A SETTINGS WINDOW FOR STUFF LIKE THE ANIMATIONS, FOCUS OPACITY ETC
// TODO CHECK USER TESTING SCREENCAPS, TO SEE FOR HOW LONGER THEIR CURSOR IS NEAR TOOLBAR ICON?
// TODO MAKE SELECTION KNOW WHEN USING CMD + SHIFT + ARROW
// TODO make KBS into a mananagable list
// TODO make KBS shortcut fit into box
// TODO remove justify button + unneeded color button
// TODO MAKE THE WINDOW SCALABLE AND MAKE KBS' FIT
// change the shortcuts into single key KBS

// make KBS list
public class KBSManager extends VBox {

    boolean focus = true;
    KLM klm = new KLM();

//    //TODO THIS DELIBLE?
//    Timer timer = new Timer();
//    boolean movement = false;
//
//    public void callingFunctionOnTimer() {
//        int delay = 5000;
//        int period = 5000;
//        movement = false;
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (!movement) {
//                    System.out.println("NO MOVEMENT DETECTED");
//                }
//                movement = false;
//                //write what should happen here
//                System.out.println("time had passed");
//            }
//        }, delay, period);
//    }

    void registerOnMovement() {

    }

    private String oprSystem = System.getProperty("os.name");


    boolean show = true;

    void show(boolean show) {
        this.setVisible(show);
        this.setManaged(show);
    }

    void toggleShow() {
        System.out.println("KBSMANAGER SHOW TOGGLED");
        if (show) {
            this.setVisible(false);
            this.setManaged(false);
            this.show = false;
        } else {
            this.setVisible(true);
            this.setManaged(true);
            this.show = true;
        }
    }
    //public BooleanBinding test;

    boolean mouseLock;

    KBSManager(String oprSystem) {
        oprSystem = System.getProperty("os.name");
    }

    void AddButtonOrFunctionAsKBS(Button buttonObjectToMaybeReturn,
                                  String Functionality,
                                  String ToolbarImage) {
    }

    public KBS getKBSbyFunction(Object data) {
        for (Node n : this.getChildren()) {
            if (data.equals(n.getUserData())) {
                return (KBS) n;
            }
        }
        return null;
    }

    KBSManager() {
        this.setMouseTransparent(false);
//        callingFunctionOnTimer();
        setMaxSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        LOGGER.info("Operation system " + oprSystem); //Logs operation system
        String modifier; //Show shortcut, changes depending on operating system
        //Changes the string "modifier" depending on the operating system.
        if (oprSystem.contains("Windows")) {
            modifier = "Ctrl";
        } else {
            modifier = "⌘";
        }

        //this.setOrientation(VERTICAL);
        this.setPadding(new Insets(10));
        this.setSpacing(5);

        //this.setStyle("-fx-border-color: black");
        this.setAlignment(BOTTOM_RIGHT);
        this.getChildren().addAll(
                new KBS(modifier + " + B", "bold", "org/fxmisc/richtext/demo/richtext/BiconHR.png"),
                new KBS(modifier + " + I", "italic", "org/fxmisc/richtext/demo/richtext/IiconHR.png"),
                new KBS(modifier + " + U", "underline", "org/fxmisc/richtext/demo/richtext/UiconHR.png"),
                new KBS(modifier + " + T", "strikethrough", "org/fxmisc/richtext/demo/richtext/SiconHR.png"),
                new KBS(modifier + " + P", "insertimage", "org/fxmisc/richtext/demo/richtext/AddImageIconHR.png"),
                new KBS(modifier + " + L", "align-left", "org/fxmisc/richtext/demo/richtext/ALiconHR.png"),
                new KBS(modifier + " + E", "align-center", "org/fxmisc/richtext/demo/richtext/ACiconHR.png"),
                new KBS(modifier + " + R", "align-right", "org/fxmisc/richtext/demo/richtext/ARiconHR.png"),
                new KBS(modifier + " + J", "align-justify", "org/fxmisc/richtext/demo/richtext/AJiconHR.png")
                //new KBS("Ctrl + Fuck", "image", "asdfasfd")
        );
        this.setUpHovers();
    }

    public FadeTransition fade(double opacityEnd, double time) {

        double opacityStart = this.getOpacity();

        FadeTransition fade = new FadeTransition(Duration.seconds(time), this);
        fade.setFromValue(opacityStart);
        fade.setToValue(opacityEnd);
        //fade.setCycleCount(Timeline.INDEFINITE);
        //fade.setAutoReverse(true);
        fade.play(); //start animation

        return fade; // TODO REASONS FOR THIS RETURNING FADE?

        //this.setOnMousePressed(e -> System.out.println("adasfdf"));
    }

    public FadeTransition fade(double opacityEnd, double time, Node node) {

        double opacityStart = this.getOpacity();

        FadeTransition fade = new FadeTransition(Duration.seconds(time), node);
        fade.setFromValue(opacityStart);
        fade.setToValue(opacityEnd);
        //fade.setCycleCount(Timeline.INDEFINITE);
        //fade.setAutoReverse(true);
        fade.play(); //start animation

        return fade; // TODO REASONS FOR THIS RETURNING FADE?

        //this.setOnMousePressed(e -> System.out.println("adasfdf"));
    }

    public void setUpHovers() {
//        this.setOnMouseMoved(event -> {
//            System.out.println(event.getSceneX() + " " + event.getSceneY());
////            System.out.println();
//        });
        this.setOnKeyPressed((e -> {
            System.out.println("ENTER");
//            this.mouseLock = true;
//            this.fade(1, 0.2);

        }));
        this.setOnMouseEntered((e -> {
            this.mouseLock = true;
            System.out.println("ENTER");
//            this.fade(1, 0.2);
//            this.onSelect();
        }));
        this.setOnMouseExited((e -> {
            this.mouseLock = false;
            System.out.println("EXIT");
//            this.fade(0.2, 0.2);
//            this.offSelect();
        }));
    }

    public void onSelect() {
        for (Node n : this.getChildren()) {
            if (n.getClass().equals(KBS.class)) {
                KBS k = ((KBS) n);
                if (k.isShown) {
                    k.setOpacity(1.);
                    k.colorRect.setOpacity(0);
                }
            }
        }
    }

    public void offSelect() {
        for (Node n : this.getChildren()) {
            if (n.getClass().equals(KBS.class)) {
                KBS k = ((KBS) n);
                if (k.isShown) {
                    k.setOpacity(.2);
                    k.colorRect.setOpacity(0);
                }
            }
        }
    }
    public void parseMouse(double x, double y) {
        System.out.println("KM OPACITY: " + this.getOpacity() + "       MOUSELOCK: " + this.mouseLock);
        for (Node n : this.getChildren()) {
            if (n.getClass().equals(KBS.class)) {
                KBS k = ((KBS) n);
                if (k.isShown) {
                    double numMus = y - 12.5;
                    double opaScaled = this.scaleFunc(numMus, 0, 100, 1., 0.2);
                    if (!mouseLock) {
                        k.setOpacity(opaScaled);

                    } else {
                        k.setOpacity(1.);
                    }
                    if (k.convinceOMeter.isBeingAnimatedConvince) {
                        k.setOpacity(1);
                    }
                    double num = Math.sqrt(Math.pow(x - k.buttonX, 2) + Math.pow(y - k.buttonY, 2));


                    double scaled = this.scaleFunc(num, 0, 100, 1., 0);
                    if (k.attentionable && !k.didit) {
                        k.colorRect.setOpacity(scaled);
                    }

                    if (mouseLock) {
//                        k.colorRect.setOpacity(0);
                    }

//                    System.out.println("scaled: " + scaled);


//                    System.out.println("DISTANCE to " + k.functionality + ": " + num + " SCALED: " + scaled);
                    System.out.println("FUNC: " + k.functionality + "           COLORECTOPA: " + k.colorRect.getOpacity() + "           KOPA: " + k.getOpacity());
                }

            }
        }

    }

    public void disableColor() {
        for (Node n : this.getChildren()) {
            KBS k = ((KBS) n);
            if (!k.didit && k.isShown) {
                this.fade(0., 0.35, k.colorRect);
//                k.colorRect.setOpacity(0.);
            }
        }
    }


    double scaleFunc(double input, double in_min, double in_max, double out_min, double out_max) {
        double calc = out_min + ((input - in_min) / (in_max - in_min)) * (out_max - out_min);
        if (calc < out_max)
            return out_max;
        else if (calc > out_min)
            return out_min;
        else
            return calc;

    }

    public void animationFix() {
        for (Node n : this.getChildren()) {
            KBS k = ((KBS) n);
            k.convinceOMeter.hide();
            k.rewardOMeter.hide();
            k.hide();
        }
    }

    void fuck() {
        for (Node n : this.getChildren()) {
            if (n.getClass().equals(KBS.class)) {
                KBS k = ((KBS) n);

            }
        }
    }
}
