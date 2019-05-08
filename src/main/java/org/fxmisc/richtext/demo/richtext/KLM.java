package org.fxmisc.richtext.demo.richtext;

import java.awt.*;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

public class KLM {

    //operator variables declared with their estimated execution times
    private double k = 0.2; //keystroke
    private double p = 1.1; //point with mouse to a target on the display
    private double b = 0.1; //press or release mouse button
    private double bb = 0.2; //click mouse button
    private double h = 0.4; //home hands to keyboard or mouse

    private double toolbarEstimate;
    private double shortcutEstimate;

    private double startTimeToolbar;
    private double stopTimeToolbar;
    private double timeElapsedForToolbar;

    private double startTimeShortcut;
    private double stopTimeShortcut;
    private double timeElapsedForShortcut;

    KLM() {

    }

    public double getGeneralShortcutEstimate() {
        return this.k*2;
    }

    public double getShortcutEstimate() {
        return this.shortcutEstimate;
    }

    public double getGeneralToolbarEstimate() {
        return this.h + this.p + this.bb;
    }

    public double getToolbarEstimate() {
        return this.toolbarEstimate;
    }

    public void setToolbarEstimate(Button toolbarButton) {

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
        this.toolbarEstimate = this.h + fittsLaw + this.bb;

    }

    public void setToolbarEstimate(ToggleButton toolbarButton) {

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
        this.toolbarEstimate = this.h + fittsLaw + this.bb;

    }

    public void setShortcutEstimate(int numberOfKeystrokes) {
        this.shortcutEstimate = this.k*numberOfKeystrokes;
    }

    public void startTimerForToolbar() {
        startTimeToolbar = System.nanoTime();
    }

    public void stopTimerForToolbar() {
        stopTimeToolbar = System.nanoTime();
        double time = stopTimeToolbar-startTimeToolbar;
        this.timeElapsedForToolbar = time/1_000_000_000;
        startTimeToolbar = System.nanoTime();
    }

    public double getTimeLost() {
        return this.timeElapsedForToolbar-this.getShortcutEstimate();
    }

    public double getTimesSlower() {
        return this.timeElapsedForToolbar/this.getShortcutEstimate();
    }

    public void startTimerForShortcut() {
        startTimeShortcut = System.nanoTime();
    }

    public void stopTimerForShortcut() {
        stopTimeShortcut = System.nanoTime();
        double time = stopTimeShortcut-startTimeShortcut;
        this.timeElapsedForShortcut = time/1_000_000_000;
        System.out.println("Timer has stopped: Time = " + getTimeElapsedForShortcut());
        startTimeShortcut = System.nanoTime();
    }

    public double getTimeElapsedForShortcut() {
        return this.timeElapsedForShortcut;
    }

    public double getTimeSaved() {
        return this.timeElapsedForShortcut-this.getToolbarEstimate();
    }

    static double log(double base, double x) {
        return Math.log10(x)/Math.log10(base);
    }
}
