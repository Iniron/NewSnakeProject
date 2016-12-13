package com.project.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Layout.fxml"));	//fxmlloader »ý¼º
	    Parent root = fxmlLoader.load();												//fxmlload
	    
	    Scene scene = new Scene(root, 300, 300);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tetris Game");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
