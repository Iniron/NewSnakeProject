package com.project.snake.app;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class NotificationPanel extends BorderPane {

    public NotificationPanel(String text) {
        //setMinHeight(200);
        //setMinWidth(220);
        final Label score = new Label(text);
        score.setOpacity(0.8);
        if(text.equals("level Up!")){
        	score.getStyleClass().add("levelUpStyle");
        	
        }
        else{
        	score.getStyleClass().add("bonusStyle");
        	//score.setTextFill(Color.WHITE);
        }
        final Effect glow = new Glow(0.6);
        score.setEffect(glow);
        
        setCenter(score);
    }

    public void showScore(StackPane stackPane) {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), this);
        TranslateTransition tt = new TranslateTransition(Duration.millis(2500), this);
        tt.setToY(this.getLayoutY() - 40);
        ft.setFromValue(1);
        ft.setToValue(0);
        ParallelTransition transition = new ParallelTransition(tt, ft);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	stackPane.getChildren().remove(NotificationPanel.this);
            }
        });
        transition.play();
    }
}
