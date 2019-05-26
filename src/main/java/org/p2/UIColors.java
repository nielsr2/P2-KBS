package org.p2;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public abstract class UIColors {

    public static Color getBorderColor() {
        return Color.DARKGRAY;
    }

    public static Color getTextAlertColor() {
        return Color.RED;
    }

    public static Color getTextApprovalColor() {
        return Color.GREEN;
    }

    public static Color getBackgroundColor() {
        return Color.LIGHTGREY;
    }


    static LinearGradient getSuggestionAlertColor() {
        Color colorLeft = Color.web("#FF5252");
        Color colorRight = Color.web("#C72020");

        Stop[] stopsColor = new Stop[]{new Stop(0, colorRight), new Stop(1, colorLeft)};
        LinearGradient linearGradient = new LinearGradient(1, 0, 0, 0, true, CycleMethod.NO_CYCLE, stopsColor);

        return linearGradient;
    }

    static LinearGradient getSuggestionColor() {
        Color colorLeft = Color.web("#DDDDDD");
        Color colorRight = Color.web("#CCCCCC");

        Stop[] stopsColor = new Stop[]{new Stop(0, colorRight), new Stop(1, colorLeft)};
        LinearGradient linearGradient = new LinearGradient(1, 0, 0, 0, true, CycleMethod.NO_CYCLE, stopsColor);

        return linearGradient;
    }

    static LinearGradient getNotificationsColor() {
        Color colorLeft = Color.web("#CCCCCF");
        Color colorRight = Color.web("#BBBBBC");

        Stop[] stopsColor = new Stop[]{new Stop(0, colorRight), new Stop(1, colorLeft)};
        LinearGradient linearGradient = new LinearGradient(1, 0, 0, 0, true, CycleMethod.NO_CYCLE, stopsColor);

        return linearGradient;
    }

    public static LinearGradient getBackgroundGradient() {
        Color colorLeft = Color.web("#EEEFFF");
        Color colorRight = Color.web("#405087");

        Stop[] stopsColor = new Stop[]{new Stop(0, colorRight), new Stop(1, colorLeft)};
        LinearGradient linearGradient = new LinearGradient(0.07, 1, 0, -0.20, true, CycleMethod.NO_CYCLE, stopsColor);

        return linearGradient;
    }
}
