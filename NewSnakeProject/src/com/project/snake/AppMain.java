package com.project.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Layout1.fxml"));	//fxmlloader »ý¼º
	    Parent root = fxmlLoader.load();											//fxmlload
	    ViewController cnt_view = fxmlLoader.getController();
	    cnt_view.setPrimaryStage(primaryStage);
	    
	    Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Snake Game");
		//primaryStage.setFullScreen(true);
		//primaryStage.setMaximized(true);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
