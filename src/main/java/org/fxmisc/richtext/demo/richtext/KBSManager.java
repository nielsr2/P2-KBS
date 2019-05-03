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

    KBSManager() {
        setMaxSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);


        //this.setOrientation(VERTICAL);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setSpacing(5);
        //this.set(5);
        this.setStyle("-fx-border-color: black");
        this.setAlignment(BOTTOM_CENTER);
        this.getChildren().addAll(
                new KBS("Ctrl + B", "bold", "org/fxmisc/richtext/demo/richtext/bold.png"),
                new KBS("Ctrl + I", "italic", "org/fxmisc/richtext/demo/richtext/italic.png"),
                new KBS("Ctrl + Fuck", "strikethrough", "org/fxmisc/richtext/demo/richtext/strikethrough.png"),
                new KBS("Ctrl + Fuck", "underline", "org/fxmisc/richtext/demo/richtext/underline.png"),
                new KBS("Ctrl + Fuck", "alignRight", "org/fxmisc/richtext/demo/richtext/align-right.png"),
                new KBS("Ctrl + Fuck", "alignLeft", "org/fxmisc/richtext/demo/richtext/align-left.png"),
                new KBS("Ctrl + Fuck", "alignCenter", "org/fxmisc/richtext/demo/richtext/align-center.png"),
                new KBS("Ctrl + Fuck", "alignJustify", "org/fxmisc/richtext/demo/richtext/align-justify.png")
                //new KBS("Ctrl + Fuck", "image", "asdfasfd")
        );
        this.getKBSbyFunction("bold").setVisible(true); // TODO 123 : Kristinn will MAKE A FUNCTION FOR THIS INSIDE KBS, THAT TOGGLES THESE TWO FUNCTION ()
        this.getKBSbyFunction("bold").setManaged(true);
        this.getKBSbyFunction("italic").setVisible(true); // TODO 123 : MAKE A FUNCTION FOR THIS INSIDE KBS, THAT TOGGLES THESE TWO FUNCTION ()
        this.getKBSbyFunction("italic").setManaged(true);
        this.getKBSbyFunction("bold").anim();
        this.setUpHovers();

        this.getKBSbyFunction("bold").show(true);
        this.getKBSbyFunction("italic").show(true);
        this.getKBSbyFunction("strikethrough").show(true);
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
            this.setOpacity();
        }));
        this.setOnMouseExited((e -> {
            System.out.println("EXIT");
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


