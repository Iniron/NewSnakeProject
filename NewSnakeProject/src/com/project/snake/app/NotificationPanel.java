package com.project.snake.app;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class NotificationPanel extends BorderPane {

    public NotificationPanel(String text) {
        final Label score = new Label(text);				//Label 생성
        score.setOpacity(0.8);								//투명도 설정
        if(text.equals("Level Up!")){						//Level Up과 아닌경우 서로 다른 Style을 적용
        	score.getStyleClass().add("levelUpStyle");
        }
        else{
        	score.getStyleClass().add("bonusStyle");
        }
        final Effect glow = new Glow(0.6);					//Glow 효과
        score.setEffect(glow);
        setCenter(score);
    }

    //FadeTranstion효과
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
