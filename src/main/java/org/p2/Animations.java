package org.p2;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

abstract class Animations {

//    double standardTime = 0.2;

    static void fade(double opacityEnd, double time, Node node) {

        double opacityStart = node.getOpacity();

        FadeTransition fade = new FadeTransition(Duration.seconds(time), node);
        fade.setFromValue(opacityStart);
        fade.setToValue(opacityEnd);
        //fade.setCycleCount(Timeline.INDEFINITE);
        //fade.setAutoReverse(true);
        fade.play(); //start animation
    }

    static void fade(double opacityEnd, Node node) {

        double opacityStart = node.getOpacity();

        FadeTransition fade = new FadeTransition(Duration.seconds(0.2), node);
        fade.setFromValue(opacityStart);
        fade.setToValue(opacityEnd);
        //fade.setCycleCount(Timeline.INDEFINITE);
        //fade.setAutoReverse(true);
        fade.play(); //start animation
    }
}
