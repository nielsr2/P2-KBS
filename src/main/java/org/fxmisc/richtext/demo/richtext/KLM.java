package org.fxmisc.richtext.demo.richtext;

import java.awt.*;
import javafx.scene.control.Button;

public class KLM {

    //operator variables declared with their estimated execution times
    private double k = 0.2; //keystroke
    private double p = 1.1; //point with mouse to a target on the display
    private double b = 0.1; //press or release mouse button
    private double bb = 0.2; //click mouse button
    private double h = 0.4; //home hands to keyboard or mouse

    KLM() {

    }

    public double getShortcutEstimate() {
        return this.k*2;
    }

    public double getShortcutEstimate(int numberOfKeystrokes) {
        return this.k*numberOfKeystrokes;
    }

    public double getToolbarEstimate() {
        return this.h + this.p + this.bb;
    }

    public double getToolbarEstimate(/*HERE WE NEED SPECIFIED TOOLBAR BUTTON INFO*/ Button toolbarButton) {

        /*mouse position start*/
        PointerInfo startMouse = MouseInfo.getPointerInfo();
        double endMouseX = toolbarButton.localToScreen(toolbarButton.getBoundsInLocal()).getMinX()+toolbarButton.getWidth()/2;
        double endMouseY = toolbarButton.localToScreen(toolbarButton.getBoundsInLocal()).getMinY()+toolbarButton.getHeight()/2;
        double startMouseX = startMouse.getLocation().getX();
        double startMouseY = startMouse.getLocation().getY();
        double width = toolbarButton.getWidth();
        double distance = Math.sqrt(Math.pow(startMouseX-endMouseX, 2) + Math.pow(startMouseY-endMouseY, 2));
        double a = 0; //We don't take the mental factor into account
        double b = 0.5;
        double fittsLaw = a+b*log(2, distance/width + 1); // Fitt's Law
        return this.h + fittsLaw + this.bb;

    }

     static double log(double base, double x) {
        return Math.log10(x)/Math.log10(base);
    }
}
