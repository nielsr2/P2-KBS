package org.fxmisc.richtext.demo.richtext;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public interface Animations {


    double standardTime = 0.2;

    static FadeTransition fade(double opacityEnd, double time, Node node) {

        double opacityStart = node.getOpacity();

        FadeTransition fade = new FadeTransition(Duration.seconds(time), node);
        fade.setFromValue(opacityStart);
        fade.setToValue(opacityEnd);
        //fade.setCycleCount(Timeline.INDEFINITE);
        //fade.setAutoReverse(true);
        fade.play(); //start animation

        return fade; // TODO REASONS FOR THIS RETURNING FADE?

        //this.setOnMousePressed(e -> System.out.println("adasfdf"));
    }

    static FadeTransition fade(double opacityEnd, Node node) {

        double opacityStart = node.getOpacity();

        FadeTransition fade = new FadeTransition(Duration.seconds(standardTime), node);
        fade.setFromValue(opacityStart);
        fade.setToValue(opacityEnd);
        //fade.setCycleCount(Timeline.INDEFINITE);
        //fade.setAutoReverse(true);
        fade.play(); //start animation

        return fade; // TODO REASONS FOR THIS RETURNING FADE?

        //this.setOnMousePressed(e -> System.out.println("adasfdf"));
    }
}
