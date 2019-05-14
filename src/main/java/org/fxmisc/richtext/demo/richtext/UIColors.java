package org.fxmisc.richtext.demo.richtext;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public interface UIColors {

    Color borderColor = Color.DARKGRAY;
    Color textAlertColor = Color.RED;
    Color textApprovalColor = Color.GREEN;
    Color backgrondColor = Color.LIGHTGREY;

    static LinearGradient setKBSAlertColor() {
        Color colorLeft = Color.web("#FF5252");
        Color colorRight = Color.web("#C72020");

        Stop[] stopsColor = new Stop[]{new Stop(0, colorRight), new Stop(1, colorLeft)};
        LinearGradient linearGradient = new LinearGradient(1, 0, 0, 0, true, CycleMethod.NO_CYCLE, stopsColor);

        return linearGradient;
    }

    static LinearGradient setKBSColor() {
        Color colorLeft = Color.web("#DDDDDD");
        Color colorRight = Color.web("#CCCCCC");

        Stop[] stopsColor = new Stop[]{new Stop(0, colorRight), new Stop(1, colorLeft)};
        LinearGradient linearGradient = new LinearGradient(1, 0, 0, 0, true, CycleMethod.NO_CYCLE, stopsColor);

        return linearGradient;
    }

    static LinearGradient setSkillOMeterColor() {
        Color colorLeft = Color.web("#CCCCCF");
        Color colorRight = Color.web("#BBBBBC");

        Stop[] stopsColor = new Stop[]{new Stop(0, colorRight), new Stop(1, colorLeft)};
        LinearGradient linearGradient = new LinearGradient(1, 0, 0, 0, true, CycleMethod.NO_CYCLE, stopsColor);

        return linearGradient;
    }

    static LinearGradient setBackgroundColor() {
        Color colorLeft = Color.web("#EEEFFF");
        Color colorRight = Color.web("#204067");

        Stop[] stopsColor = new Stop[]{new Stop(0, colorRight), new Stop(1, colorLeft)};
        LinearGradient linearGradient = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stopsColor);

        return linearGradient;
    }
}
