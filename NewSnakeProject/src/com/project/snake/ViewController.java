package com.project.snake;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ViewController implements Initializable {
	
	@FXML
	private GridPane gamePanel;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Rectangle rect = new Rectangle(50, 50);
		rect.setFill(Color.RED);
		
		gamePanel.add(rect, 0, 0);
		
	}

}
