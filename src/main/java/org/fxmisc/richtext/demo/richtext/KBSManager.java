package org.fxmisc.richtext.demo.richtext;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

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
public class KBSManager extends VBox implements Animations {

    KLM klm = new KLM();
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




    // used to return KBSs by ID e.g. bold
    public KBS getKBSbyFunction(String data) {
        for (Node n : this.getChildren()) { // getChildren only return nodes,since all javafx elements extends note (kbs extends HBox, which extends note) , is ok
            if (data.equals(n.getId())) { // compare input with id's of the KBS's ( which are set in constructor )
                return (KBS) n;
            }
        }
        return null;
    }

    //                       _                   _
    //    ___ ___  _ __  ___| |_ _ __ _   _  ___| |_ ___  _ __
    //   / __/ _ \| '_ \/ __| __| '__| | | |/ __| __/ _ \| '__|
    //  | (__ (_) | | | \__ \ |_| |  | |_| | (__| |_ (_) | |
    //   \___\___/|_| |_|___/\__|_|   \__,_|\___|\__\___/|_|
    //


    KBSManager() {

        // SETUP
        this.setMouseTransparent(false);
        setMaxSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        //this.setOrientation(VERTICAL);
        this.setPadding(new Insets(10));
        this.setSpacing(5);
        //this.setStyle("-fx-border-color: black");
        this.setAlignment(BOTTOM_RIGHT);

        // OS
        LOGGER.info("Operation system " + oprSystem); //Logs operation system
        String modifier; //Show shortcut, changes depending on operating system
        if (oprSystem.contains("Windows")) {    //Changes the string "modifier" depending on the operating system.
            modifier = "Ctrl";
        } else {
            modifier = "⌘";
        }

        // ADD
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

    public void animationFix() {
        for (Node n : this.getChildren()) {
            KBS k = ((KBS) n);
            k.convinceOMeter.hide();
            k.rewardOMeter.hide();
            k.hide();
        }
    }

    //   _
    //  | |__   _____   _____ _ __
    //  | '_ \ / _ \ \ / / _ \ '__|
    //  | | | | (_) \ V /  __/ |
    //  |_| |_|\___/ \_/ \___|_|
    //
    boolean kmHovered, anIconHovered, textSelected;
    //
    //   _ __ ___   ___  _   _ ___  ___
    //  | '_ ` _ \ / _ \| | | / __|/ _ \
    //  | | | | | | (_) | |_| \__ \  __/
    //  |_| |_| |_|\___/ \__,_|___/\___|
    //
    double fadeMax = 1.;

    //            _           _   _
//   ___  ___| | ___  ___| |_(_) ___  _ __
//  / __|/ _ \ |/ _ \/ __| __| |/ _ \| '_ \
//  \__ \  __/ |  __/ (__| |_| | (_) | | | |
//  |___/\___|_|\___|\___|\__|_|\___/|_| |_|
//
    public void selectionOn() {
        System.out.println("SELECTION ON DETECTED");
        for (Node n : this.getChildren()) {
            if (n.getClass().equals(KBS.class)) {
                KBS k = ((KBS) n);
                if (k.isShown) {
                    Animations.fade(fadeMax, k);
                    k.colorRect.setOpacity(0);
                }
            }
        }
    }

    public void selectionOff() {
        System.out.println("SELECTION OFF DETECTED");
        for (Node n : this.getChildren()) {
            if (n.getClass().equals(KBS.class)) {
                KBS k = ((KBS) n);
                if (k.isShown) {
                    Animations.fade(fadeMin, k);
                    k.colorRect.setOpacity(0);
                }
            }
        }
    }

    double fadeMin = 0.5;
    double colorDistance = 70;
    double opacityDistance = 100;

    public void setUpHovers() {
        this.setOnKeyPressed((e -> {
            System.out.println("ENTER");
            //  this.mouseLock = true;
            Animations.fade(1, this);

        }));
        this.setOnMouseEntered((e -> {
            this.kmHovered = true;
            System.out.println("ENTER");
//                        this.fade(1, 0.2);
            this.selectionOn();
        }));
        this.setOnMouseExited((e -> {
            this.kmHovered = false;
            System.out.println("EXIT");
//            this.fade(0.2, 0.2);
            this.selectionOff();
        }));
    }

    public void parseMouse(double x, double y) {
//        System.out.println("KM OPACITY: " + this.getOpacity() + "       MOUSELOCK: " + this.mouseLock);

        // opacity for all base on mouse to toolbar y axis
        double mouseIconDistanceY = y - 12.5;
        double opacityY = this.scaleFunc(mouseIconDistanceY, 0, colorDistance, fadeMax + .2, fadeMin);

        for (Node n : this.getChildren()) {
            if (n.getClass().equals(KBS.class)) {
                KBS k = ((KBS) n);
                double mouseIconDistanceXY = Math.sqrt(Math.pow(x - k.buttonX, 2) + Math.pow(y - k.buttonY, 2));
                double colorOpacityXY = this.scaleFunc(mouseIconDistanceXY, 0, 100, 1., 0);

                double opacityXY = this.scaleFunc(mouseIconDistanceXY, 0, 100, fadeMax + .2, fadeMin);


                if (k.isShown) {
                    // if NOT mouse locked (is not hovered on toolbaricon, etc)


                    if (anIconHovered || kmHovered) {
                        k.setOpacity(1.);   // full opacity if any icon hovered
                    } else {
                        if (!textSelected) {
                            k.setOpacity(opacityY); // gradual opacity
                        }
                        double mouseIconDistance = Math.sqrt(Math.pow(x - k.buttonX, 2) + Math.pow(y - k.buttonY, 2));
                        double colorOpacity = this.scaleFunc(mouseIconDistance, 0, opacityDistance, 1., 0);
                        k.colorRect.setOpacity(colorOpacity);
                    }
                    // if showing messages, don't be faded
                    if (k.convinceOMeter.isBeingAnimatedConvince) {
                        k.setOpacity(1);
                    }
                }

            }
        }

    }

    public void disableColor() {
        for (Node n : this.getChildren()) {
            KBS k = ((KBS) n);
            if (!k.hovered && k.isShown) {
                Animations.fade(0., 0.3, k.colorRect);
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

}
