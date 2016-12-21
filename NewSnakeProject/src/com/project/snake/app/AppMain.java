package com.project.snake.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Resource/Layout1.fxml"));	//fxmlloader 생성
	    Parent root = fxmlLoader.load();																			//fxmlload
	    
	    Scene scene = new Scene(root);				//Scene 생성, fxml을 붙인다.
		primaryStage.setScene(scene);				//Stage에 Scene 부착
		primaryStage.setResizable(false);			//창크기 변경 불가
		primaryStage.setTitle("Snake Game");		//게임 타이틀
		//primaryStage.setFullScreenExitHint("");
		//primaryStage.setFullScreen(true);
		//primaryStage.setMaximized(true);
		primaryStage.show();						//화면 출력
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
