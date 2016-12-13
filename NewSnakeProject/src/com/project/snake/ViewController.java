package com.project.snake;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ViewController implements Initializable {
	
	public static final int height = 20;
	public static final int width = 20;
	public static final int RECT_SIZE = 20;
	
	@FXML
	private GridPane gamePanel;
	
	Rectangle[][] gameRects;
	LinkedList<Point> snake =  new LinkedList<>();
	Point head;
	Point fruit;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		initGameView();
		
		head = new Point(height/2, width/2);
		int x = head.getX(); 
		int y = head.getY();
		gameRects[y][x].setFill(Color.RED);
		snake.add(head);
		
		fruit = new Point(2, 2);
		gameRects[2][2].setFill(Color.GREEN);
		
		gamePanel.setFocusTraversable(true);
		gamePanel.requestFocus();
		gamePanel.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.UP){
				checkCrash(-1, 0);
				repaintSnake(snake);
			}
			if(event.getCode() == KeyCode.RIGHT){
				checkCrash(0, 1);
				repaintSnake(snake);
			}
			if(event.getCode() == KeyCode.LEFT){
				checkCrash(0, -1);
				repaintSnake(snake);
			}
			if(event.getCode() == KeyCode.DOWN){
				checkCrash(1, 0);
				repaintSnake(snake);
			}
		});
	}
	
	public void checkCrash(int off_y, int off_x){
		int y = head.getY() + off_y;
		int x = head.getX() + off_x;
		if(x<0 || x>width-1 || y<0 || y>height-1 || gameRects[y][x].getFill()==Color.RED){
			System.out.println("게임오버");
			return;
		}
		if(gameRects[y][x].getFill()==Color.GREEN){
			snake.add(fruit);
			head =  fruit;
			return;
		}
		head = new Point(y, x);
		snake.add(head);
		snake.poll();
	}
	
	public void repaintSnake(LinkedList<Point> snake){
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				gameRects[i][j].setFill(Color.BLACK);
				if(fruit.getX()==j && fruit.getY()==i){
					gameRects[j][i].setFill(Color.GREEN);
				}
			}
		}
		for(int i=0; i<snake.size(); i++){
			int y = snake.get(i).getY();
			int x = snake.get(i).getX();			
			gameRects[y][x].setFill(Color.RED);

		}
	}
	
	public void initGameView(){
		Rectangle rect;
		gameRects = new Rectangle[height][width];			//set gamePanel
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				rect = new Rectangle(RECT_SIZE, RECT_SIZE);
				rect.setFill(Color.BLACK);
				rect.setStroke(Color.WHITE);
				gameRects[i][j] = rect;
				gamePanel.add(rect, j, i);
			}
		}
	}

}
