package org.p2;


import java.awt.*;

public interface Settings {

    //public final static String WINDOW_TITLE = "SimpleRichTextFX Demo";

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double WINDOW_WIDTH = screenSize.getWidth() / 3 * 2;

    double WINDOW_HEIGHT = screenSize.getHeight() / 3 * 2 - 25;
    double PAPER_WIDTH = 600;
    double PAPER_HEIGHT = PAPER_WIDTH * 1.4;


}
