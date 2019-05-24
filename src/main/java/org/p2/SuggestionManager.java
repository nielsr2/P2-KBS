package org.p2;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import static javafx.geometry.Pos.BOTTOM_RIGHT;

// TODO CHECK IF STUFF SHOULD BE PRIVATE, PUBLIC ETC.
// change the shortcuts into single key Suggestion

// make Suggestion list
public class SuggestionManager extends VBox {

    public KLM klm = new KLM();
    private String oprSystem = System.getProperty("os.name");


    //                       _                   _
    //    ___ ___  _ __  ___| |_ _ __ _   _  ___| |_ ___  _ __
    //   / __/ _ \| '_ \/ __| __| '__| | | |/ __| __/ _ \| '__|
    //  | (__ (_) | | | \__ \ |_| |  | |_| | (__| |_ (_) | |
    //   \___\___/|_| |_|___/\__|_|   \__,_|\___|\__\___/|_|
    //


    //   _
    //  | |__   _____   _____ _ __
    //  | '_ \ / _ \ \ / / _ \ '__|
    //  | | | | (_) \ V /  __/ |
    //  |_| |_|\___/ \_/ \___|_|
    //
    boolean suggestionManagerHovered;

    SuggestionManager(double width, double height) {

        // SETUP
        this.setMouseTransparent(false);
        setMaxSize(width, height);
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
                new Suggestion(modifier + " + B", "bold", "org/fxmisc/richtext/demo/richtext/BiconHR.png"),
                new Suggestion(modifier + " + I", "italic", "org/fxmisc/richtext/demo/richtext/IiconHR.png"),
                new Suggestion(modifier + " + U", "underline", "org/fxmisc/richtext/demo/richtext/UiconHR.png"),
                new Suggestion(modifier + " + T", "strikethrough", "org/fxmisc/richtext/demo/richtext/SiconHR.png"),
                new Suggestion(modifier + " + P", "insertimage", "org/fxmisc/richtext/demo/richtext/AddImageIconHR.png"),
                new Suggestion(modifier + " + L", "align-left", "org/fxmisc/richtext/demo/richtext/ALiconHR.png"),
                new Suggestion(modifier + " + E", "align-center", "org/fxmisc/richtext/demo/richtext/ACiconHR.png"),
                new Suggestion(modifier + " + R", "align-right", "org/fxmisc/richtext/demo/richtext/ARiconHR.png"),
                new Suggestion(modifier + " + J", "align-justify", "org/fxmisc/richtext/demo/richtext/AJiconHR.png")
        );

        this.setUpHovers();
    }
    boolean anyIconHovered;
    boolean textSelected;

    public void setAnyIconHovered(boolean anyIconHovered) {
        this.anyIconHovered = anyIconHovered;
    }

    // this ensures that animations starting with opacity 0 does not show with full opacity for a split second.
    // The function is and needs to be called after the scene has been created. Non-instantiated cannot be hidden.
    public void animationFix() {
        for (Node n : this.getChildren()) {
            Suggestion s = ((Suggestion) n);
            s.rewardNotification.hide();
            s.convinceNotification.hide();
            s.hide();
        }
    }

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

        this.textSelected = true;
        for (Node n : this.getChildren()) {
            if (n.getClass().equals(Suggestion.class)) {
                Suggestion s = ((Suggestion) n);
                if (s.isShown) {
                    Animations.fade(fadeMax, s);
                    s.colorRect.setOpacity(0);
                }
            }
        }
    }

    public void selectionOff() {
        System.out.println("SELECTION OFF DETECTED");
        this.textSelected = false;
        for (Node n : this.getChildren()) {
            if (n.getClass().equals(Suggestion.class)) {
                Suggestion s = ((Suggestion) n);
                if (s.isShown) {
                    Animations.fade(fadeMin, s);
                    s.colorRect.setOpacity(0);
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
            this.suggestionManagerHovered = true;
            System.out.println("ENTER");
//                        this.fade(1, 0.2);
            this.selectionOn();
        }));
        this.setOnMouseExited((e -> {
            this.suggestionManagerHovered = false;
            System.out.println("EXIT");
//            this.fade(0.2, 0.2);
            this.selectionOff();
        }));
    }

    public void parseMouse(double x, double y) {
        // opacity for all based on mouse to toolbar distance, y axis
        double mouseIconDistanceY = y - 12.5;
        double opacityY = this.scaleAndClip(
                mouseIconDistanceY, 0, colorDistance, fadeMax + .2, fadeMin);
        for (Node n : this.getChildren()) {
            if (n.getClass().equals(Suggestion.class)) {
                Suggestion s = ((Suggestion) n);
                if (s.isShown) { // only affect Suggestion's that are shown...
                    if (s.canAnimate) {
                        // if any toolbar icon or Suggestion area hovered, full opacity
                        if (anyIconHovered || suggestionManagerHovered) {
                            s.setOpacity(1.);
                        } else {
                            if (!textSelected) {
                                s.setOpacity(opacityY); // gradual opacity
                            }
                            double mouseIconDistance = Math.sqrt(
                                    Math.pow(x - s.buttonX, 2) + Math.pow(y - s.buttonY, 2));
                            double colorOpacity = this.scaleAndClip(
                                    mouseIconDistance, 0, opacityDistance, 1., 0);
                            s.colorRect.setOpacity(colorOpacity);
                        }
                        // if showing messages, don't be faded
                        if (s.convinceNotification.isBeingAnimated) {
                            s.setOpacity(1);
                        }
                    }
                }
            }
        }
    }

    public void disableColor() {
        for (Node n : this.getChildren()) {
            Suggestion s = ((Suggestion) n);
            if (!s.hovered && s.isShown) {
                Animations.fade(0., 0.3, s.colorRect);
            }
        }
    }

    boolean show = true;

    double scaleAndClip(double input, double in_min, double in_max, double out_min, double out_max) {
        double calc = out_min + ((input - in_min) / (in_max - in_min)) * (out_max - out_min);
        if (calc < out_max)
            return out_max;
        else if (calc > out_min)
            return out_min;
        else
            return calc;

    }

    // used to return Suggestions by ID e.g. bold
    public Suggestion getSuggestionbyFunction(String data) {
        for (Node n : this.getChildren()) { // getChildren only return nodes,since all javafx elements extends note (suggestion extends HBox, which extends note) , is ok
            if (data.equals(n.getId())) { // compare input with id's of the Suggestion's ( which are set in constructor )
                return (Suggestion) n;
            }
        }
        return null;
    }

    void show(boolean show) {
        this.setVisible(show);
        this.setManaged(show);
    }

    public void toggleShow() {
        System.out.println("SuggestionMANAGER SHOW TOGGLED");
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

    public void toggleActivation() {
        for (Node n : this.getChildren()) {
            if (n.getClass().equals(Suggestion.class)) {
                Suggestion s = ((Suggestion) n);
                s.toggleActivation();
            }
        }
    }
}
