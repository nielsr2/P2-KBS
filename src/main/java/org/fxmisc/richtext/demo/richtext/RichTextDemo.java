/*
 * Created 2014 by Tomas Mikula.
 *
 * The author dedicates this file to the public domain.
 */

package org.fxmisc.richtext.demo.richtext;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.StyledTextArea;
import org.fxmisc.richtext.TextExt;
import org.fxmisc.richtext.model.*;
import org.p2.MyLogger;
import org.p2.OverlayPane;
import org.p2.UIColors;
import org.reactfx.SuspendableNo;
import org.reactfx.util.Either;
import org.reactfx.util.Tuple2;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.Logger;

import static org.fxmisc.richtext.model.TwoDimensional.Bias.Backward;
import static org.fxmisc.richtext.model.TwoDimensional.Bias.Forward;

//import static org.p2.UIColors.getBackgroundGradient;

public class RichTextDemo extends Application {

    // the saved/loaded files and their format are arbitrary and may change across versions
    private static final String RTFX_FILE_EXTENSION = ".rtfx";


    // ################################################################
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double WINDOW_WIDTH = screenSize.getWidth() / 3 * 2;

    double WINDOW_HEIGHT = screenSize.getHeight() / 3 * 2 - 25;
    double PAPER_WIDTH = 600;
    double PAPER_HEIGHT = PAPER_WIDTH * 1.4;
    // ################################################################



    // *** LOG FUNCATIONALITY
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    // ################################################################
    PointerInfo startMouse, endMouse;
    // moved outside of start()
    Button boldBtn, italicBtn, underlineBtn, strikeBtn, insertImageBtn;
    // overlaypane that combines elements correctly
    OverlayPane overlayPane = new OverlayPane(WINDOW_WIDTH, WINDOW_HEIGHT);
    ToggleButton alignLeftBtn, alignCenterBtn, alignRightBtn, alignJustifyBtn;
    // ################################################################

    private final TextOps<String, TextStyle> styledTextOps = SegmentOps.styledTextOps();
    private final LinkedImageOps<TextStyle> linkedImageOps = new LinkedImageOps<>();

    private final GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> area =
            new GenericStyledArea<>(
                    ParStyle.EMPTY,                                                 // default paragraph style
                    (paragraph, style) -> paragraph.setStyle(style.toCss()),        // paragraph style setter

                    TextStyle.EMPTY.updateFontSize(12).updateFontFamily("Serif").updateTextColor(Color.BLACK),  // default segment style
                    styledTextOps._or(linkedImageOps, (s1, s2) -> Optional.empty()),                            // segment operations
                    seg -> createNode(seg, (text, style) -> text.setStyle(style.toCss())));                     // Node creator and segment style setter

    {
        area.setWrapText(true);
        area.setStyleCodecs(
                ParStyle.CODEC,
                Codec.styledSegmentCodec(Codec.eitherCodec(Codec.STRING_CODEC, LinkedImage.codec()), TextStyle.CODEC));
    }

    private Stage mainStage;

    private final SuspendableNo updatingToolbar = new SuspendableNo();

    // ################################################################
    public static void main(String[] args) {
        // The following properties are required on Linux for improved text rendering
        //System.setProperty("prism.lcdtext", "false");
        //System.setProperty("prism.text", "t2k");

        // ################################################################
        //Tries to create a log file for data gathering if it fails it throws an IOException.
        try {
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }
        // ###############################################################
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;

        Button loadBtn = createButton("loadfile", this::loadDocument,
                "Load document.\n\n" +
                        "Note: the demo will load only previously-saved \"" + RTFX_FILE_EXTENSION + "\" files. " +
                        "This file format is abitrary and may change across versions.");
        Button saveBtn = createButton("savefile", this::saveDocument,
                "Save document.\n\n" +
                        "Note: the demo will save the area's content to a \"" + RTFX_FILE_EXTENSION + "\" file. " +
                        "This file format is abitrary and may change across versions.");
        CheckBox wrapToggle = new CheckBox("Wrap");
        wrapToggle.setSelected(true);
        area.wrapTextProperty().bind(wrapToggle.selectedProperty());
        Button undoBtn = createButton("undo", area::undo, "Undo");
        Button redoBtn = createButton("redo", area::redo, "Redo");
        Button cutBtn = createButton("cut", area::cut, "Cut");
        Button copyBtn = createButton("copy", area::copy, "Copy");
        Button pasteBtn = createButton("paste", area::paste, "Paste");
// ################################################################
// ################################################################
// ################################################################

        // TOGGLE OUR SOLUTION VISIBILITY
        KeyCombination kcShow = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        Runnable rnShow = () -> overlayPane.suggestionManager.toggleShow(); //this.toggleBold();
//     BOLD BUTTON  *********************************************************************************************************           BOLD BUTTON
        boldBtn = createButton("bold", this::toggleBold, "Bold (CMD/CTRL+B)");
        boldBtn.setOnMouseClicked((event) -> {
            overlayPane.suggestionManager.klm.stopTimer("toolbar");
            overlayPane.suggestionManager.getSuggestionbyFunction("bold").convinceNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesSlower());
            overlayPane.suggestionManager.getSuggestionbyFunction("bold").toolbarPressed();
        });
//        boldBtn
        KeyCombination kcBold = new KeyCodeCombination(KeyCode.B, KeyCombination.SHORTCUT_DOWN);
        Mnemonic mnBold = new Mnemonic(boldBtn, kcBold);
        Runnable rnBold = () -> this.ourBoldFunction(); //this.toggleBold();
//      *********************************************************************************************************

        ///////////////// ITALIC BUTTON /////////////////////////////
        italicBtn = createButton("italic", this::toggleItalic, "Italic (CMD/CTRL+I)");
        italicBtn.setOnMouseClicked((event) -> {
            overlayPane.suggestionManager.klm.stopTimer("toolbar");
            overlayPane.suggestionManager.getSuggestionbyFunction("italic").convinceNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesSlower());
            overlayPane.suggestionManager.getSuggestionbyFunction("italic").toolbarPressed();
        });
        KeyCombination kcItalic = new KeyCodeCombination(KeyCode.I, KeyCombination.SHORTCUT_DOWN);
        Mnemonic mnItalic = new Mnemonic(italicBtn, kcItalic);
        Runnable rnItalic = () -> ourItalicFunction(); //this.toggleItalic

        ////////////////// UNDERLINE BUTTON /////////////////////////
        underlineBtn = createButton("underline", this::toggleUnderline, "Underline (CMD/CTRL+U)");
        underlineBtn.setOnMouseClicked((event) -> {
            overlayPane.suggestionManager.klm.stopTimer("toolbar");
            overlayPane.suggestionManager.getSuggestionbyFunction("underline").convinceNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesSlower());
            overlayPane.suggestionManager.getSuggestionbyFunction("underline").toolbarPressed();
        });
        KeyCombination kcUnderline = new KeyCodeCombination(KeyCode.U, KeyCombination.SHORTCUT_DOWN);
        Mnemonic mnUnderline = new Mnemonic(underlineBtn, kcUnderline);
        Runnable rnUnderline = () -> ourUnderlineFunction();//this.toggleUnderline();

        ///////////////// STRIKE THROUGH BUTTON ////////////////////
        strikeBtn = createButton("strikethrough", this::toggleStrikethrough, "Strike Trough (CMD/CTRL+T)");
        strikeBtn.setOnMouseClicked((event) -> {
            overlayPane.suggestionManager.klm.stopTimer("toolbar");
            overlayPane.suggestionManager.getSuggestionbyFunction("strikethrough").convinceNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesSlower());
            overlayPane.suggestionManager.getSuggestionbyFunction("strikethrough").toolbarPressed();
        });
        KeyCombination kcStrike = new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN);
        Mnemonic mnStrike = new Mnemonic(strikeBtn, kcStrike);
        Runnable rnStrike = () -> ourStrikeFunction();//this.toggleStrikethrough();

        //////////////// INSERT IMAGE BUTTON //////////////////////
        insertImageBtn = createButton("insertimage", this::insertImage, "Insert Image");
        insertImageBtn.setOnMouseClicked((event) -> {
            overlayPane.suggestionManager.klm.stopTimer("toolbar");
            overlayPane.suggestionManager.getSuggestionbyFunction("insertimage").convinceNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesSlower());
            overlayPane.suggestionManager.getSuggestionbyFunction("insertimage").toolbarPressed();
        });
        KeyCombination kcInsertImage = new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN);
        Mnemonic mnInsertImage = new Mnemonic(insertImageBtn, kcInsertImage);
        Runnable rnInsertImage = () -> ourInsertImageFunction();//this.insertImage();

        ToggleGroup alignmentGrp = new ToggleGroup();

        /////////////// ALIGN LEFT BUTTON ////////////////////////
        alignLeftBtn = createToggleButton(alignmentGrp, "align-left", this::alignLeft, "Align left (CMD/CTRL+L)");

        alignLeftBtn.setOnMouseClicked((event) -> {
            overlayPane.suggestionManager.klm.stopTimer("toolbar");
            overlayPane.suggestionManager.getSuggestionbyFunction("align-left").convinceNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesSlower());
            overlayPane.suggestionManager.getSuggestionbyFunction("align-left").toolbarPressed();
        });
        KeyCombination kcAlignLeft = new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN);
        Mnemonic mnAlignLeft = new Mnemonic(alignLeftBtn, kcAlignLeft);
        Runnable rnAlignLeft = () -> ourAlignLeftFunction();//this.alignLeft();

        ////////////// ALIGN CENTER BUTTON //////////////////////
        alignCenterBtn = createToggleButton(alignmentGrp, "align-center", this::alignCenter, "Align center (CMD/CTRL+E)");
        alignCenterBtn.setOnMouseClicked((event) -> {
            overlayPane.suggestionManager.klm.stopTimer("toolbar");
            overlayPane.suggestionManager.getSuggestionbyFunction("align-center").convinceNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesSlower());
            overlayPane.suggestionManager.getSuggestionbyFunction("align-center").toolbarPressed();
        });
        KeyCombination kcAlignCenter = new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN);
        Mnemonic mnAlignCenter = new Mnemonic(alignCenterBtn, kcAlignCenter);
        Runnable rnAlignCenter = () -> ourAlignCenterFunction();//this.alignCenter();

        ///////////// ALIGN RIGHT BUTTON ///////////////////////
        alignRightBtn = createToggleButton(alignmentGrp, "align-right", this::alignRight, "Align right (CMD/CTRL+R)");
        alignRightBtn.setOnMouseClicked((event) -> {
            overlayPane.suggestionManager.klm.stopTimer("toolbar");
            overlayPane.suggestionManager.getSuggestionbyFunction("align-right").convinceNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesSlower());
            overlayPane.suggestionManager.getSuggestionbyFunction("align-right").toolbarPressed();
        });
        KeyCombination kcAlignRight = new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN);
        Mnemonic mnAlignRight = new Mnemonic(alignRightBtn, kcAlignLeft);
        Runnable rnAlignRight = () -> ourAlignRightFunction();//this.alignRight();

        //////////// ALIGN JUSTIFY /////////////////////////
        alignJustifyBtn = createToggleButton(alignmentGrp, "align-justify", this::alignJustify, "Justify (CMD/CTRL+J)");
        alignJustifyBtn.setOnMouseClicked((event) -> {
            overlayPane.suggestionManager.klm.stopTimer("toolbar");
            overlayPane.suggestionManager.getSuggestionbyFunction("align-justify").convinceNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesSlower());
            overlayPane.suggestionManager.getSuggestionbyFunction("align-justify").toolbarPressed();
        });
        KeyCombination kcAlignJustify = new KeyCodeCombination(KeyCode.J, KeyCombination.SHORTCUT_DOWN);
        Mnemonic mnAlignJustify = new Mnemonic(alignJustifyBtn, kcAlignJustify);
        Runnable rnAlignJustify = () -> ourAlignJustifyFunction();//this.alignJustify();

// ################################################################
// ################################################################
// ################################################################
// ################################################################
        ColorPicker paragraphBackgroundPicker = new ColorPicker();
        ComboBox<Integer> sizeCombo = new ComboBox<>(FXCollections.observableArrayList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16, 18, 20, 22, 24, 28, 32, 36, 40, 48, 56, 64, 72));
        sizeCombo.getSelectionModel().select(Integer.valueOf(12));
        sizeCombo.setTooltip(new Tooltip("Font size"));
        ComboBox<String> familyCombo = new ComboBox<>(FXCollections.observableList(Font.getFamilies()));
        familyCombo.getSelectionModel().select("Serif");
        familyCombo.setTooltip(new Tooltip("Font family"));
        ColorPicker textColorPicker = new ColorPicker(Color.BLACK);
        ColorPicker backgroundColorPicker = new ColorPicker();

        paragraphBackgroundPicker.setTooltip(new Tooltip("Paragraph background"));
        textColorPicker.setTooltip(new Tooltip("Text color"));
        backgroundColorPicker.setTooltip(new Tooltip("Text background"));

        paragraphBackgroundPicker.valueProperty().addListener((o, old, color) -> updateParagraphBackground(color));
        sizeCombo.setOnAction(evt -> updateFontSize(sizeCombo.getValue()));
        familyCombo.setOnAction(evt -> updateFontFamily(familyCombo.getValue()));
        textColorPicker.valueProperty().addListener((o, old, color) -> updateTextColor(color));
        backgroundColorPicker.valueProperty().addListener((o, old, color) -> updateBackgroundColor(color));

        undoBtn.disableProperty().bind(area.undoAvailableProperty().map(x -> !x));
        redoBtn.disableProperty().bind(area.redoAvailableProperty().map(x -> !x));

        BooleanBinding selectionEmpty = new BooleanBinding() {
            {
                bind(area.selectionProperty());
            }

            @Override
            protected boolean computeValue() {
                return area.getSelection().getLength() == 0;
            }
        };

        cutBtn.disableProperty().bind(selectionEmpty);
        copyBtn.disableProperty().bind(selectionEmpty);


        area.beingUpdatedProperty().addListener((o, old, beingUpdated) -> {
            if (!beingUpdated) {
                boolean bold, italic, underline, strike;
                Integer fontSize;
                String fontFamily;
                Color textColor;
                Color backgroundColor;

                IndexRange selection = area.getSelection();
                if (selection.getLength() != 0) {
                    StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
                    bold = styles.styleStream().anyMatch(s -> s.bold.orElse(false));
                    italic = styles.styleStream().anyMatch(s -> s.italic.orElse(false));
                    underline = styles.styleStream().anyMatch(s -> s.underline.orElse(false));
                    strike = styles.styleStream().anyMatch(s -> s.strikethrough.orElse(false));
                    int[] sizes = styles.styleStream().mapToInt(s -> s.fontSize.orElse(-1)).distinct().toArray();
                    fontSize = sizes.length == 1 ? sizes[0] : -1;
                    String[] families = styles.styleStream().map(s -> s.fontFamily.orElse(null)).distinct().toArray(String[]::new);
                    fontFamily = families.length == 1 ? families[0] : null;
                    Color[] colors = styles.styleStream().map(s -> s.textColor.orElse(null)).distinct().toArray(Color[]::new);
                    textColor = colors.length == 1 ? colors[0] : null;
                    Color[] backgrounds = styles.styleStream().map(s -> s.backgroundColor.orElse(null)).distinct().toArray(i -> new Color[i]);
                    backgroundColor = backgrounds.length == 1 ? backgrounds[0] : null;
                } else {
                    int p = area.getCurrentParagraph();
                    int col = area.getCaretColumn();
                    TextStyle style = area.getStyleAtPosition(p, col);
                    bold = style.bold.orElse(false);
                    italic = style.italic.orElse(false);
                    underline = style.underline.orElse(false);
                    strike = style.strikethrough.orElse(false);
                    fontSize = style.fontSize.orElse(-1);
                    fontFamily = style.fontFamily.orElse(null);
                    textColor = style.textColor.orElse(null);
                    backgroundColor = style.backgroundColor.orElse(null);
                }

                int startPar = area.offsetToPosition(selection.getStart(), Forward).getMajor();
                int endPar = area.offsetToPosition(selection.getEnd(), Backward).getMajor();
                List<Paragraph<ParStyle, Either<String, LinkedImage>, TextStyle>> pars = area.getParagraphs().subList(startPar, endPar + 1);

                @SuppressWarnings("unchecked")
                Optional<TextAlignment>[] alignments = pars.stream().map(p -> p.getParagraphStyle().alignment).distinct().toArray(Optional[]::new);
                Optional<TextAlignment> alignment = alignments.length == 1 ? alignments[0] : Optional.empty();

                @SuppressWarnings("unchecked")
                Optional<Color>[] paragraphBackgrounds = pars.stream().map(p -> p.getParagraphStyle().backgroundColor).distinct().toArray(Optional[]::new);
                Optional<Color> paragraphBackground = paragraphBackgrounds.length == 1 ? paragraphBackgrounds[0] : Optional.empty();

                updatingToolbar.suspendWhile(() -> {
                    if (bold) {
                        if (!boldBtn.getStyleClass().contains("pressed")) {
                            boldBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        boldBtn.getStyleClass().remove("pressed");
                    }

                    if (italic) {
                        if (!italicBtn.getStyleClass().contains("pressed")) {
                            italicBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        italicBtn.getStyleClass().remove("pressed");
                    }

                    if (underline) {
                        if (!underlineBtn.getStyleClass().contains("pressed")) {
                            underlineBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        underlineBtn.getStyleClass().remove("pressed");
                    }

                    if (strike) {
                        if (!strikeBtn.getStyleClass().contains("pressed")) {
                            strikeBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        strikeBtn.getStyleClass().remove("pressed");
                    }

                    if (alignment.isPresent()) {
                        TextAlignment al = alignment.get();
                        switch (al) {
                            case LEFT:
                                alignmentGrp.selectToggle(alignLeftBtn);
                                break;
                            case CENTER:
                                alignmentGrp.selectToggle(alignCenterBtn);
                                break;
                            case RIGHT:
                                alignmentGrp.selectToggle(alignRightBtn);
                                break;
                            case JUSTIFY:
                                alignmentGrp.selectToggle(alignJustifyBtn);
                                break;
                        }
                    } else {
                        alignmentGrp.selectToggle(null);
                    }

                    paragraphBackgroundPicker.setValue(paragraphBackground.orElse(null));

                    if (fontSize != -1) {
                        sizeCombo.getSelectionModel().select(fontSize);
                    } else {
                        sizeCombo.getSelectionModel().clearSelection();
                    }
                    if (fontFamily != null) {
                        familyCombo.getSelectionModel().select(fontFamily);
                    } else {
                        familyCombo.getSelectionModel().clearSelection();
                    }

                    if (textColor != null) {
                        textColorPicker.setValue(textColor);
                    }

                    backgroundColorPicker.setValue(backgroundColor);
                });
            }
        });

        ToolBar toolBar1 = new ToolBar(
                loadBtn, saveBtn, /*new Separator(Orientation.VERTICAL),
                wrapToggle,*/ new Separator(Orientation.VERTICAL),
                undoBtn, redoBtn, new Separator(Orientation.VERTICAL),
                cutBtn, copyBtn, pasteBtn, new Separator(Orientation.VERTICAL),
                boldBtn, italicBtn, underlineBtn, strikeBtn, new Separator(Orientation.VERTICAL),
                alignLeftBtn, alignCenterBtn, alignRightBtn, alignJustifyBtn,
                new Separator(Orientation.VERTICAL), sizeCombo, familyCombo
                /*, insertImageBtn, new Separator(Orientation.VERTICAL),
                paragraphBackgroundPicker*/);

//        ToolBar toolBar2 = new ToolBar(sizeCombo, familyCombo/*, textColorPicker, backgroundColorPicker*/);


        VirtualizedScrollPane<GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle>> writePane = new VirtualizedScrollPane<>(area);

        StackPane wrapperPane = new StackPane(writePane);

        wrapperPane.setMaxSize(PAPER_WIDTH - 90, PAPER_HEIGHT - 60);
        wrapperPane.setTranslateY(50);

        Rectangle paper = new Rectangle(PAPER_WIDTH, WINDOW_HEIGHT, Color.WHITE);
        paper.setTranslateY(10);
        DropShadow dropShadow = new DropShadow(30, 5, 6, Color.web("#405070"));
        paper.setEffect(dropShadow);


        StackPane vsPane = new StackPane();
        Rectangle backgroundRect = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT - toolBar1.getHeight());
        backgroundRect.setFill(UIColors.getBackgroundGradient());
        vsPane.setAlignment(Pos.TOP_CENTER);

        vsPane.setMaxHeight(WINDOW_HEIGHT - 200);
        vsPane.getChildren().addAll(backgroundRect, paper, wrapperPane);


        VBox vbox = new VBox();

        vbox.setMaxSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        VBox.setVgrow(vsPane, Priority.ALWAYS);
        vbox.getChildren().addAll(toolBar1, /*toolBar2,*/ vsPane);

// ################################################################
// ################################################################
// ################################################################
//                                             _           _   _
//   _ __ ___   ___  _   _ ___  ___   ___  ___| | ___  ___| |_(_) ___  _ __
//  | '_ ` _ \ / _ \| | | / __|/ _ \ / __|/ _ \ |/ _ \/ __| __| |/ _ \| '_ \
//  | | | | | | (_) | |_| \__ \  __/ \__ \  __/ |  __/ (__| |_| | (_) | | | |
//  |_| |_| |_|\___/ \__,_|___/\___| |___/\___|_|\___|\___|\__|_|\___/|_| |_|
//

        area.setOnMouseReleased((e -> {
//            int CurrentParagraph = area.getCurrentParagraph();
//            int CaretPosition = area.getCaretPosition();
//            int CaretColumn = area.getCaretColumn();
//            int anchor = area.getAnchor();
//            System.out.println("MOUSE RELEASED : CurrentParagraph: " + CurrentParagraph + " CaretPosition: " + CaretPosition + " CaretColumn: " + CaretColumn + " anchor: " + anchor);
            System.out.println("Selection: " + area.getSelection());
            boolean textHighlighted = !selectionEmpty.get(); // get, since selectionEmpty is a BooleanBinding, get is used to get the boolean value;
            if (textHighlighted) {
//                overlayPane.suggestionManager.fade(1, 0.2);

                overlayPane.suggestionManager.selectionOn();
//                overlayPane.suggestionManager.parseMouse(0,0);

            } else {
                overlayPane.suggestionManager.selectionOff();
//                overlayPane.suggestionManager.fade(0.2, 0.2);
                overlayPane.suggestionManager.klm.stopTimer("toolbar");
//                overlayPane.suggestionManager.parseMouse(0,0);
            }
            overlayPane.suggestionManager.klm.setTimerForToolbarAllowance(true);
        }));
        area.setOnMouseMoved((e -> {

            startMouse = MouseInfo.getPointerInfo();
            int threshold = 0;
            if (overlayPane.suggestionManager.klm.getTimerForToolbarAllowance() == true) {
                double distance = startMouse.getLocation().getX() - endMouse.getLocation().getX();
                if (distance > threshold || distance < -threshold) {
                    overlayPane.suggestionManager.klm.startTimer("toolbar");
                    overlayPane.suggestionManager.klm.setTimerForToolbarAllowance(false);
                }
            }
            endMouse = MouseInfo.getPointerInfo();
        }));


//                 _
//    ___  _ __   | | _____ _   _
//   / _ \| '_ \  | |/ / _ \ | | |
//  | (_) | | | | |   <  __/ |_| |
//   \___/|_| |_| |_|\_\___|\__, |
//                          |___/

        area.setOnKeyPressed(e -> {
//            System.out.println("KEYPRESSED: " + e);
            if (e.isShortcutDown() && e.isShiftDown()) {
                if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
                    overlayPane.suggestionManager.selectionOn();
                }
            }
            if (e.isShortcutDown() && this.overlayPane.suggestionManager.klm.getTimerForShortcutAllowance() == true) {
                //System.out.println("NOPE" + KeyCombination.SHORTCUT_DOWN);
                this.overlayPane.suggestionManager.klm.startTimer("shortcut");
                this.overlayPane.suggestionManager.klm.setTimerForShortcutAllowance(false);
//                    overlayPane.suggestionManager.fade(1, 0.2);
            }
            if (e.isShortcutDown() && e.isShiftDown() && e.getCode() == KeyCode.O) {
                this.overlayPane.suggestionManager.toggleActivation();
            }
        });
        area.setOnKeyReleased(e -> {
            //System.out.println("Key released");
            this.overlayPane.suggestionManager.klm.setTimerForShortcutAllowance(true);
        });
        // *****************************
        Pane root = new Pane();

        //   _ __   __ _ _ __ ___  ___   _ __ ___   ___  _   _ ___  ___
        //  | '_ \ / _` | '__/ __|/ _ \ | '_ ` _ \ / _ \| | | / __|/ _ \
        //  | |_) | (_| | |  \__ \  __/ | | | | | | (_) | |_| \__ \  __/
        //  | .__/ \__,_|_|  |___/\___| |_| |_| |_|\___/ \__,_|___/\___|
        //  |_|

        for (Node n : toolBar1.getItems()) {
            n.setOnMouseMoved(event -> {
                double mx = event.getSceneX();
                double my = event.getSceneY();
                overlayPane.suggestionManager.parseMouse(mx, my);
            });
        }
        toolBar1.setOnMouseMoved(event -> {
            double mx = event.getSceneX();
            double my = event.getSceneY();
            overlayPane.suggestionManager.parseMouse(mx, my);
        });
        toolBar1.setOnMouseMoved(event -> {
            double mx = event.getSceneX();
            double my = event.getSceneY();
            overlayPane.suggestionManager.parseMouse(mx, my);
        });
        root.setOnMouseMoved(event -> {
            double mx = event.getSceneX();
            double my = event.getSceneY();
            overlayPane.suggestionManager.parseMouse(mx, my);
        });

        // *************************************************************************************************************************************************

        root.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        overlayPane.setMaxSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        root.getChildren().addAll(vbox, overlayPane);
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add(RichTextDemo.class.getResource("rich-text.css").toExternalForm());
        // ################################################################
        // ################################################################
        scene.getAccelerators().put(kcShow, rnShow);
        scene.addMnemonic(mnBold);
        scene.getAccelerators().put(kcBold, rnBold);
        scene.addMnemonic(mnItalic);
        scene.getAccelerators().put(kcItalic, rnItalic);
        scene.addMnemonic(mnUnderline);
        scene.getAccelerators().put(kcUnderline, rnUnderline);
        scene.addMnemonic(mnStrike);
        scene.getAccelerators().put(kcStrike, rnStrike);
        scene.addMnemonic(mnAlignLeft);
        scene.getAccelerators().put(kcAlignLeft, rnAlignLeft);
        scene.addMnemonic(mnAlignCenter);
        scene.getAccelerators().put(kcAlignCenter, rnAlignCenter);
        scene.addMnemonic(mnAlignRight);
        scene.getAccelerators().put(kcAlignRight, rnAlignRight);
        scene.addMnemonic(mnAlignJustify);
        scene.getAccelerators().put(kcAlignJustify, rnAlignJustify);
// ################################################################
// ################################################################


        primaryStage.setScene(scene);
        area.requestFocus();
        primaryStage.setTitle("a word processor application");
        primaryStage.show();
// ################################################################
// ################################################################
        this.setUpGradualAttention("bold", boldBtn);
        this.setUpGradualAttention("italic", italicBtn);
        this.setUpGradualAttention("underline", underlineBtn);
        this.setUpGradualAttention("strikethrough", strikeBtn);
        this.setUpGradualAttention("align-right", alignRightBtn);
        this.setUpGradualAttention("align-left", alignLeftBtn);
        this.setUpGradualAttention("align-center", alignCenterBtn);
        this.setUpGradualAttention("align-justify", alignJustifyBtn);
        overlayPane.suggestionManager.animationFix();
        area.requestFocus();
        // ################################################################
        // ################################################################
    }

    private Node createNode(StyledSegment<Either<String, LinkedImage>, TextStyle> seg,
                            BiConsumer<? super TextExt, TextStyle> applyStyle) {
        return seg.getSegment().unify(
                text -> StyledTextArea.createStyledTextNode(text, seg.getStyle(), applyStyle),
                LinkedImage::createNode
        );
    }

    @Deprecated
    private Button createButton(String styleClass, Runnable action) {
        return createButton(styleClass, action, null);
    }

    private Button createButton(String styleClass, Runnable action, String toolTip) {
        Button button = new Button();
        button.getStyleClass().add(styleClass);
        button.setOnAction(evt -> {
            action.run();
            area.requestFocus();
        });
        button.setPrefWidth(25);
        button.setPrefHeight(25);
        if (toolTip != null) {
            button.setTooltip(new Tooltip(toolTip));
        }
        return button;
    }

    private ToggleButton createToggleButton(ToggleGroup grp, String styleClass, Runnable action, String toolTip) {
        ToggleButton button = new ToggleButton();
        button.setToggleGroup(grp);
        button.getStyleClass().add(styleClass);
        button.setOnAction(evt -> {
            action.run();
            area.requestFocus();
        });
        button.setPrefWidth(25);
        button.setPrefHeight(25);
        if (toolTip != null) {
            button.setTooltip(new Tooltip(toolTip));
        }
        return button;
    }

    private void toggleBold() {
        updateStyleInSelection(spans -> TextStyle.bold(!spans.styleStream().allMatch(style -> style.bold.orElse(false))));
    }

    private void toggleItalic() {
        updateStyleInSelection(spans -> TextStyle.italic(!spans.styleStream().allMatch(style -> style.italic.orElse(false))));
    }

    private void toggleUnderline() {
        updateStyleInSelection(spans -> TextStyle.underline(!spans.styleStream().allMatch(style -> style.underline.orElse(false))));
    }

    private void toggleStrikethrough() {
        updateStyleInSelection(spans -> TextStyle.strikethrough(!spans.styleStream().allMatch(style -> style.strikethrough.orElse(false))));
    }

    private void alignLeft() {
        updateParagraphStyleInSelection(ParStyle.alignLeft());
    }

    private void alignCenter() {
        updateParagraphStyleInSelection(ParStyle.alignCenter());
    }

    private void alignRight() {
        updateParagraphStyleInSelection(ParStyle.alignRight());
    }

    private void alignJustify() {
        updateParagraphStyleInSelection(ParStyle.alignJustify());
    }

    private void loadDocument() {
        String initialDir = System.getProperty("user.dir");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load document");
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("Arbitrary RTFX file", "*" + RTFX_FILE_EXTENSION));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            area.clear();
            load(selectedFile);
        }
    }

    private void load(File file) {
        if (area.getStyleCodecs().isPresent()) {
            Tuple2<Codec<ParStyle>, Codec<StyledSegment<Either<String, LinkedImage>, TextStyle>>> codecs = area.getStyleCodecs().get();
            Codec<StyledDocument<ParStyle, Either<String, LinkedImage>, TextStyle>>
                    codec = ReadOnlyStyledDocument.codec(codecs._1, codecs._2, area.getSegOps());

            try {
                FileInputStream fis = new FileInputStream(file);
                DataInputStream dis = new DataInputStream(fis);
                StyledDocument<ParStyle, Either<String, LinkedImage>, TextStyle> doc = codec.decode(dis);
                fis.close();

                if (doc != null) {
                    area.replaceSelection(doc);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void saveDocument() {
        String initialDir = System.getProperty("user.dir");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save document");
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.setInitialFileName("example rtfx file" + RTFX_FILE_EXTENSION);
        File selectedFile = fileChooser.showSaveDialog(mainStage);
        if (selectedFile != null) {
            save(selectedFile);
        }
    }


    private void save(File file) {
        StyledDocument<ParStyle, Either<String, LinkedImage>, TextStyle> doc = area.getDocument();

        // Use the Codec to save the document in a binary format
        area.getStyleCodecs().ifPresent(codecs -> {
            Codec<StyledDocument<ParStyle, Either<String, LinkedImage>, TextStyle>> codec =
                    ReadOnlyStyledDocument.codec(codecs._1, codecs._2, area.getSegOps());
            try {
                FileOutputStream fos = new FileOutputStream(file);
                DataOutputStream dos = new DataOutputStream(fos);
                codec.encode(dos, doc);
                fos.close();
            } catch (IOException fnfe) {
                fnfe.printStackTrace();
            }
        });
    }


    /**
     * Action listener which inserts a new image at the current caret position.
     */
    private void insertImage() {
        String initialDir = System.getProperty("user.dir");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Insert image");
        fileChooser.setInitialDirectory(new File(initialDir));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            imagePath = imagePath.replace('\\', '/');
            ReadOnlyStyledDocument<ParStyle, Either<String, LinkedImage>, TextStyle> ros =
                    ReadOnlyStyledDocument.fromSegment(Either.right(new RealLinkedImage(imagePath)),
                            ParStyle.EMPTY, TextStyle.EMPTY, area.getSegOps());
            area.replaceSelection(ros);
        }
    }

    private void updateStyleInSelection(String style) {
        area.setStyle("-fx-font-weight:" + style + ";");
    }

    private void updateStyleInSelection(Function<StyleSpans<TextStyle>, TextStyle> mixinGetter) {
        IndexRange selection = area.getSelection();

        if (selection.getLength() != 0) {
            System.out.println("selection.getLength(): " + selection.getLength());
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            TextStyle mixin = mixinGetter.apply(styles);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
        // ################################################################
        // ################################################################
        // ################################################################
        if (selection.getLength() == 0) {
            int CurrentParagraph = area.getCurrentParagraph();
            int CaretPosition = area.getCaretPosition();
            int CaretColumn = area.getCaretColumn();
            int anchor = area.getAnchor();
//            System.out.println("CurrentParagraph: " + CurrentParagraph + " CaretPosition: " + CaretPosition + " CaretColumn: " + CaretColumn + " anchor: " + anchor);
//            System.out.println("Selection: " + area.getSelection());
            area.insertText(CurrentParagraph, CaretColumn, "\u200B"); // insert invisible zero-width space after caret

            IndexRange sel = area.getSelection();
            area.selectRange(sel.getStart() - 1, sel.getStart()); // move caret after character added
            sel = area.getSelection();
            StyleSpans<TextStyle> styles = area.getStyleSpans(sel);
            TextStyle mixin = mixinGetter.apply(styles);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(sel.getStart(), newStyles);
            area.deselect();

        }
        // ################################################################
        // ################################################################
        // ################################################################
    }

    private void updateStyleInSelection(TextStyle mixin) {
        IndexRange selection = area.getSelection();
        StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
        StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
        area.setStyleSpans(selection.getStart(), newStyles);
    }

    private void updateParagraphStyleInSelection(Function<ParStyle, ParStyle> updater) {
        IndexRange selection = area.getSelection();
        int startPar = area.offsetToPosition(selection.getStart(), Forward).getMajor();
        int endPar = area.offsetToPosition(selection.getEnd(), Backward).getMajor();
        for (int i = startPar; i <= endPar; ++i) {
            Paragraph<ParStyle, Either<String, LinkedImage>, TextStyle> paragraph = area.getParagraph(i);
            area.setParagraphStyle(i, updater.apply(paragraph.getParagraphStyle()));
        }
    }

    private void updateParagraphStyleInSelection(ParStyle mixin) {
        updateParagraphStyleInSelection(style -> style.updateWith(mixin));
    }

    private void updateFontSize(Integer size) {
        if (!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.fontSize(size));
        }
    }

    private void updateFontFamily(String family) {
        if (!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.fontFamily(family));
        }
    }

    private void updateTextColor(Color color) {
        if (!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.textColor(color));
        }
    }

    private void updateBackgroundColor(Color color) {
        if (!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.backgroundColor(color));
        }
    }

    private void updateParagraphBackground(Color color) {
        if (!updatingToolbar.get()) {
            updateParagraphStyleInSelection(ParStyle.backgroundColor(color));
        }
    }

    // ################################################################
    // ################################################################
    // ################################################################


//                       _             _         _   _             _   _
//    __ _ _ __ __ _  __| |_   _  __ _| |   __ _| |_| |_ ___ _ __ | |_(_) ___  _ __
//   / _` | '__/ _` |/ _` | | | |/ _` | |  / _` | __| __/ _ \ '_ \| __| |/ _ \| '_ \
//  | (_| | | | (_| | (_| | |_| | (_| | | | (_| | |_| |_  __/ | | | |_| | (_) | | | |
//   \__, |_|  \__,_|\__,_|\__,_|\__,_|_|  \__,_|\__|\__\___|_| |_|\__|_|\___/|_| |_|
//   |___/

    // functions for setting up what happens when mouse hovers toolbar icons. what happens is shake animation

    void setUpGradualAttention(String functionality, Button button) {
        this.overlayPane.suggestionManager.getSuggestionbyFunction(functionality).setButtonWidth(button.getWidth());
        this.overlayPane.suggestionManager.getSuggestionbyFunction(functionality).setButtonCoordinates(button.getLayoutX(), button.getLayoutY(), button.getWidth());
        button.setOnMouseEntered((e -> {
//            if (this.overlayPane.suggestionManager.getSuggestionbyFunction(functionality).canAnimate) {
            this.overlayPane.suggestionManager.getSuggestionbyFunction(functionality).hoverShake();
            this.overlayPane.suggestionManager.getSuggestionbyFunction(functionality).setHovered(true);
            this.overlayPane.suggestionManager.disableColor();
            this.overlayPane.suggestionManager.setAnyIconHovered(true);
//            System.out.println("this.overlayPane.suggestionManager.anyIconHovered: " + this.overlayPane.suggestionManager.anyIconHovered);
//            }
        }));
        button.setOnMouseExited((e -> {
            this.overlayPane.suggestionManager.getSuggestionbyFunction(functionality).setHovered(false);
            this.overlayPane.suggestionManager.setAnyIconHovered(false);
//            System.out.println("this.overlayPane.suggestionManager.anyIconHovered: " + this.overlayPane.suggestionManager.anyIconHovered);
        }));
    }

    void setUpGradualAttention(String functionality, ToggleButton button) {
        this.overlayPane.suggestionManager.getSuggestionbyFunction(functionality).setButtonWidth(button.getWidth());
        this.overlayPane.suggestionManager.getSuggestionbyFunction(functionality).setButtonCoordinates(button.getLayoutX(), button.getLayoutY(), button.getWidth());
        button.setOnMouseEntered((e -> {
//            if (this.overlayPane.suggestionManager.getSuggestionbyFunction(functionality).canAnimate) {
            this.overlayPane.suggestionManager.getSuggestionbyFunction(functionality).hoverShake();
            this.overlayPane.suggestionManager.getSuggestionbyFunction(functionality).setHovered(true);
            this.overlayPane.suggestionManager.disableColor();
            this.overlayPane.suggestionManager.setAnyIconHovered(true);
//            }
        }));
        button.setOnMouseExited((e -> {
            this.overlayPane.suggestionManager.getSuggestionbyFunction(functionality).setHovered(false);
            this.overlayPane.suggestionManager.setAnyIconHovered(false);
        }));
    }


    // ################################################################
    // ################################################################
    // ################################################################
    // ################################################################
// ################################################################

    void ourBoldFunction() {
        //ystem.out.println("void ourBoldFunction() called");
//        System.out.println(selectionEmpty);
        this.overlayPane.suggestionManager.klm.stopTimer("shortcut");
        this.overlayPane.suggestionManager.klm.setToolbarEstimate(boldBtn);
        this.overlayPane.suggestionManager.klm.setTimerForShortcutAllowance(false);
        this.overlayPane.suggestionManager.getSuggestionbyFunction("bold").rewardNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesFaster());
        this.overlayPane.suggestionManager.getSuggestionbyFunction("bold").shortcutUsed();
        this.toggleBold();
    }

    void ourItalicFunction() {
        this.overlayPane.suggestionManager.klm.stopTimer("shortcut");
        this.overlayPane.suggestionManager.klm.setToolbarEstimate(italicBtn);
        this.overlayPane.suggestionManager.klm.setTimerForShortcutAllowance(false);
        this.overlayPane.suggestionManager.getSuggestionbyFunction("italic").rewardNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesFaster());
        this.overlayPane.suggestionManager.getSuggestionbyFunction("italic").shortcutUsed();
        System.out.println(WINDOW_WIDTH);
        this.toggleItalic();
    }

    void ourUnderlineFunction() {
        this.overlayPane.suggestionManager.klm.stopTimer("shortcut");
        this.overlayPane.suggestionManager.klm.setToolbarEstimate(underlineBtn);
        this.overlayPane.suggestionManager.klm.setTimerForShortcutAllowance(false);
        this.overlayPane.suggestionManager.getSuggestionbyFunction("underline").rewardNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesFaster());
        this.overlayPane.suggestionManager.getSuggestionbyFunction("underline").shortcutUsed();
        this.toggleUnderline();
    }

    void ourStrikeFunction() {
        this.overlayPane.suggestionManager.klm.stopTimer("shortcut");
        this.overlayPane.suggestionManager.klm.setToolbarEstimate(strikeBtn);
        this.overlayPane.suggestionManager.klm.setTimerForShortcutAllowance(false);
        this.overlayPane.suggestionManager.getSuggestionbyFunction("strikethrough").rewardNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesFaster());
        this.overlayPane.suggestionManager.getSuggestionbyFunction("strikethrough").shortcutUsed();
        this.toggleStrikethrough();
    }

    void ourInsertImageFunction() {
        this.overlayPane.suggestionManager.klm.stopTimer("shortcut");
        this.overlayPane.suggestionManager.klm.setToolbarEstimate(insertImageBtn);
        this.overlayPane.suggestionManager.klm.setTimerForShortcutAllowance(false);
        this.overlayPane.suggestionManager.getSuggestionbyFunction("insertimage").rewardNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesFaster());
        this.overlayPane.suggestionManager.getSuggestionbyFunction("insertimage").shortcutUsed();
        this.insertImage();
    }

    void ourAlignLeftFunction() {
        this.overlayPane.suggestionManager.klm.stopTimer("shortcut");
        this.overlayPane.suggestionManager.klm.setToolbarEstimate(alignLeftBtn);
        this.overlayPane.suggestionManager.klm.setTimerForShortcutAllowance(false);
        this.overlayPane.suggestionManager.getSuggestionbyFunction("align-left").rewardNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesFaster());
        this.overlayPane.suggestionManager.getSuggestionbyFunction("align-left").shortcutUsed();
        this.alignLeft();
    }

    void ourAlignCenterFunction() {
        this.overlayPane.suggestionManager.klm.stopTimer("shortcut");
        this.overlayPane.suggestionManager.klm.setToolbarEstimate(alignCenterBtn);
        this.overlayPane.suggestionManager.klm.setTimerForShortcutAllowance(false);
        this.overlayPane.suggestionManager.getSuggestionbyFunction("align-center").rewardNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesFaster());
        this.overlayPane.suggestionManager.getSuggestionbyFunction("align-center").shortcutUsed();
        this.alignCenter();
    }

    void ourAlignRightFunction() {
        this.overlayPane.suggestionManager.klm.stopTimer("shortcut");
        this.overlayPane.suggestionManager.klm.setToolbarEstimate(alignRightBtn);
        this.overlayPane.suggestionManager.klm.setTimerForShortcutAllowance(false);
        this.overlayPane.suggestionManager.getSuggestionbyFunction("align-right").rewardNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesFaster());
        this.overlayPane.suggestionManager.getSuggestionbyFunction("align-right").shortcutUsed();
        this.alignRight();
    }

    void ourAlignJustifyFunction() {
        this.overlayPane.suggestionManager.klm.stopTimer("shortcut");
        this.overlayPane.suggestionManager.klm.setToolbarEstimate(alignJustifyBtn);
        this.overlayPane.suggestionManager.klm.setTimerForShortcutAllowance(false);
        this.overlayPane.suggestionManager.getSuggestionbyFunction("align-justify").rewardNotification.setSkillNr(overlayPane.suggestionManager.klm.getTimesFaster());
        this.overlayPane.suggestionManager.getSuggestionbyFunction("align-justify").shortcutUsed();
        this.alignJustify();
    }
// ################################################################
// ################################################################
}
