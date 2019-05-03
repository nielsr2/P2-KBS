package org.fxmisc.richtext.demo.richtext;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.awt.event.MouseEvent;

import static javafx.geometry.Pos.BOTTOM_CENTER;


public class KBSManager extends VBox {
    boolean focus = true;
    private String oprSystem = System.getProperty("os.name");

    KBSManager() {
        setMaxSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        System.out.println(this.oprSystem);
        String modifier;
        if (oprSystem.contains("Windows")) {
            modifier = "Ctrl";
        }
        else {
            modifier = "Cmd";
        }

        //this.setOrientation(VERTICAL);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setSpacing(5);
        //this.set(5);
        this.setStyle("-fx-border-color: black");
        this.setAlignment(BOTTOM_CENTER);
        this.getChildren().addAll(
                new KBS(modifier + " + B", "bold", "org/fxmisc/richtext/demo/richtext/BiconHR.png"),
                new KBS(modifier + " + I", "italic", "org/fxmisc/richtext/demo/richtext/IiconHR.png"),
                new KBS(modifier + " + U", "underline", "org/fxmisc/richtext/demo/richtext/UiconHR.png"),
                new KBS(modifier + " + Shift + X", "strikethrough", "org/fxmisc/richtext/demo/richtext/SiconHR.png"),
                new KBS(modifier + " + Shift + N", "insertimage", "org/fxmisc/richtext/demo/richtext/insertimage.png"), //Missing Icon
                new KBS(modifier + " + {", "align-right", "org/fxmisc/richtext/demo/richtext/ARiconHR.png"),
                new KBS(modifier + " + |", "align-center", "org/fxmisc/richtext/demo/richtext/ACiconHR.png"),
                new KBS(modifier + " + }", "align-left", "org/fxmisc/richtext/demo/richtext/ALiconHR.png"),
                new KBS(modifier + " + Alt + |", "align-justify", "org/fxmisc/richtext/demo/richtext/AJiconHR.png")
                //new KBS("Ctrl + Fuck", "image", "asdfasfd")
        );
        this.getKBSbyFunction("bold").anim();
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
            this.setOpacity(1.);                // TODO, CAN WE CALL ANIMATIONS HERE INSTEAD, SO IT'S SMOOTH AND NOT SUDDEN ANIMATION?
        }));
        this.setOnMouseExited((e -> {
            System.out.println("EXIT");
            this.setOpacity(0.2);
        }));
    }
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


