package org.fxmisc.richtext.demo.richtext;

import com.fxexperience.javafx.animation.PulseTransition;
import com.fxexperience.javafx.animation.ShakeTransition;
import com.fxexperience.javafx.animation.SwingTransition;
import com.fxexperience.javafx.animation.WobbleTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import static javafx.geometry.Orientation.VERTICAL;
import static javafx.geometry.Pos.*;

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

    //TODO create a timer
    Timer timer = new Timer();
    boolean movement = false;

    public void callingFunctionOnTimer() {
        int delay = 5000;
        int period = 5000;
        movement = false;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!movement) {
                    System.out.println("NO MOVEMENT DETECTED");
                }
                movement = false;
                //write what should happen here
                System.out.println("time had passed");
            }
        }, delay, period);
    }

    void registerOnMovement() {

    }

    private String oprSystem = System.getProperty("os.name");


    void show(boolean show) {
        this.setVisible(show);
        this.setManaged(show);
    }
    //public BooleanBinding test;

    KBSManager() {
        callingFunctionOnTimer();
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
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setSpacing(5);

        this.setStyle("-fx-border-color: black");
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

    public void setUpHovers() {
//        this.setOnMouseMoved(event -> {
//            System.out.println(event.getSceneX() + " " + event.getSceneY());
////            System.out.println();
//        });
        this.setOnMouseEntered((e -> {
            System.out.println("ENTER");
            this.fade(1, 0.2);
        }));
        this.setOnMouseExited((e -> {
            System.out.println("EXIT");
            this.fade(0.2, 0.2);
        }));
    }

    public FadeTransition fade(double opacityEnd, double time) {

        double opacityStart = this.getOpacity();

        FadeTransition fade = new FadeTransition(Duration.seconds(time), this);
        fade.setFromValue(opacityStart);
        fade.setToValue(opacityEnd);
        //fade.setCycleCount(Timeline.INDEFINITE);
        //fade.setAutoReverse(true);
        fade.play(); //start animation

        return fade;

        //this.setOnMousePressed(e -> System.out.println("adasfdf"));
    }

    public void parseMouse(double x, double y) {
        for (Node n : this.getChildren()) {
            if (n.getClass().equals(KBS.class)) {
                KBS k = ((KBS) n);
                if (k.isShown) {
                    double num = Math.sqrt(Math.pow(x - k.buttonX, 2) + Math.pow(y - k.buttonY, 2));
                    double scaled = this.scaleFunc(num,0,200,0.4,0.2);
                    k.colorRect.setOpacity(scaled);
                    if (num < k.buttonWidth/2){
                        SwingTransition pt = new SwingTransition(k);
                        pt.play();
                        k.colorRect.setOpacity(1.);
                        System.out.println("FUCKME!!!!!FUCKME!!!!!FUCKME!!!!!FUCKME!!!!!FUCKME!!!!!FUCKME!!!!!FUCKME!!!!!FUCKME!!!!!");
                     }
                    System.out.println("DISTANCE to " + k.functionality + ": " + num + " SCALED: " + scaled);
                }
            }
        }
    }
    double scaleFunc(double input, double in_min, double in_max, double out_min, double out_max) {
        return out_min + ((input-in_min)/(in_max - in_min)) * (out_max - out_min);
    };

//        this.setOnMouseExited(new EventHandler<MouseEvent>
//                () {
//
//            @Override
//            public void handle(MouseEvent) {
//                System.out.println("EXIT");
//            }
//        });
}
//    void getKBS(String functionality) {
//        ObservableList<Node> workingCollection = FXCollections.observableArrayList(
//                this.getChildren());
//        System.out.println(workingCollection);
////        KBS found = new KBS();
////        for (KBS k: this.kbsArray) {
////            if (k.functionality == functionality)
////                found = k;
////        }
////        return found;
////        System.out.println(this.getChildren().getByTag();
////        System.out.println(this.getChildren().filtered());
//    }

//    private SVGPath _init() {
////        String path = "M 100 100 L 300 100 L 200 200 L 0 200 z";
//        // bulb shape
//        String path = "M224.68,57c-61.16,0-110.8,51.2-110.87,112.36a112.06,112.06,0,0,0,27,73.2,100.45,100.45,0,0,1,21.6,44.64c9.93,47,40.68,58.23,62.29,58.23h0c21.62,0,52.37-11.25,62.29-58.23a100.45,100.45,0,0,1,21.6-44.64,112.06,112.06,0,0,0,27-73.2C335.48,108.2,281,57,224.68,57Z";
//        SVGPath svgpath = new SVGPath();
//        svgpath.setContent(path);
//        svgpath.setStrokeWidth(2);
//        svgpath.setStroke(Color.BLACK);
//        return svgpath;
//    }
//
//
//    private void _initializeEvents() {
//        this.OUR.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println( "Hi there! You clicked me!");
//                OUR.setFill(Color.RED);
//                FadeInDownBigTransition Anim = new FadeInDownBigTransition(OUR);
//                Anim.setOnFinished(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//                        new TadaTransition(OUR).play();
//                    }
//                });
//                Anim.play();
//
//
//            }
//        });
//    }
//void setupToolbarListeners(){
//
//    parent.boldToggleButton.setOnAction(new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent event) {
//            int bold = 0;
//            KBSArray[bold].KBSused();
//        }
//    });
//
//    this.italicToggleButton.setOnAction(new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent event) {
//            int italic = 1;
//            KBSArray[italic].KBSused();
//        }
//    });
//
//    this.underlineToggleButton.setOnAction(new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent event) {
//            int underline = 2;
//            KBSArray[underline].KBSused();
//        }
//    });
//
//    this.strikethroughToggleButton.setOnAction(new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent event) {
//            int strikethrough = 3;
//            KBSArray[strikethrough].KBSused();
//        }
//    });
//
//}


