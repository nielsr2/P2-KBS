package org.fxmisc.richtext.demo.richtext;


import java.awt.*;

public interface Settings {

    //public final static String WINDOW_TITLE = "SimpleRichTextFX Demo";

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double WINDOW_WIDTH = screenSize.getWidth();

    double WINDOW_HEIGHT = screenSize.getHeight();
    double PAPER_WIDTH = 600;
    double PAPER_HEIGHT = PAPER_WIDTH * 1.4;


}
