package com.project.snake;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ViewController implements Initializable {
	
	public static final int HEGHT = 30;
	public static final int WIDTH = 30;
	public static final int RECT_SIZE = 25;
	enum Direction{	UP, DOWN, LEFT, RIGHT	}
	
	@FXML
	private GridPane gamePanel;
	
	@FXML
	private GridPane snakePanel;
	
	@FXML
	private HBox startPanel;
	
	@FXML
	private HBox pausePanel;
	
	@FXML
	private HBox overPanel;
	
	@FXML
	private Label score;	
	
	
	Stage primaryStage;
	Rectangle[][] gameRects;
	Rectangle[][] snakeRects;
	LinkedList<Point> snake =  new LinkedList<>();
	Point head;
	Point tail;
	Point fruit;
	Direction direction;
	Timeline timeline;
	int time;
	
	public void setPrimaryStage(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Font.loadFont(getClass().getResource("04B_19__.ttf").toExternalForm(), 38);
		Font.loadFont(getClass().getResource("04B_20__.ttf").toExternalForm(), 38);
		Font.loadFont(getClass().getResource("04B_31__.ttf").toExternalForm(), 38);
		Font.loadFont(getClass().getResource("TEMPLOG_.ttf").toExternalForm(), 38);
		
		initGameView();
		
		//#3bbdc4 / 60, 187, 194 판넬파란색
		
		//#2cb8d1 / 44, 184, 209 하늘색
		//#f2b233 / 240, 174, 53 주황색
		//#8342bd / 129, 66, 189 보라색
		//#e388cc / 227, 136, 203 분홍색
		//#ed6051 / 235, 95, 82 빨간색
		//#2eb094 / 48, 176, 148 초록색
		
		//
		
		
		//초기설정 -----------------------------------
		head = new Point(HEGHT/2, WIDTH/2);
		int x = head.getX(); 
		int y = head.getY();
		snakeRects[y][x].setFill(Color.web("#35b5cc"));	
		snake.add(head);
		direction = Direction.UP;
		
		fruit = new Point(2, 2);
		//snakeRects[2][2].setFill(Color.GREEN);
		snakeRects[2][2].setStyle("-fx-background-color:white;");
		time = 100;
		//초기설정 끝 ----------------------------------
		
		gamePanel.setFocusTraversable(true);
		gamePanel.requestFocus();
		gamePanel.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.UP){
				if(!direction.equals(Direction.DOWN))
					changeDirection(Direction.UP);
				event.consume();
			}
			if(event.getCode() == KeyCode.RIGHT){
				if(!direction.equals(Direction.LEFT))
					changeDirection(Direction.RIGHT);
				event.consume();
			}
			if(event.getCode() == KeyCode.LEFT){
				if(!direction.equals(Direction.RIGHT))
					changeDirection(Direction.LEFT);
				event.consume();
			}
			if(event.getCode() == KeyCode.DOWN){
				if(!direction.equals(Direction.UP))
					changeDirection(Direction.DOWN);
				event.consume();
			}
			if(event.getCode() == KeyCode.ENTER){
				//timeline.setDelay(Duration.millis(1000));
				gameStart();
			}
			if(event.getCode() == KeyCode.SPACE){
				//timeline.setDelay(Duration.millis(1000));
			}
			if(event.getCode() == KeyCode.P){
				
//				if(pausePanel.isVisible()) pausePanel.setVisible(false);
//				else						pausePanel.setVisible(true);

				//timeline.setDelay(Duration.millis(1000));
				if(timeline.getStatus()==Status.RUNNING)
				timeline.stop();
				else if(timeline.getStatus()==Status.STOPPED)
				timeline.play();
			}
		});
		
			
			
	}
	public void gameStart(){
		
		startPanel.setVisible(false);
		if(timeline==null){
			timeline = new Timeline(new KeyFrame(
	              Duration.millis(time),
	              event -> moveSnake()));
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();
		}
	}
	
	public void changeDirection(Direction direction){
		this.direction = direction;
	}
	
	public void moveSnake(){
		switch(direction){
			case UP: 	checkCrash(-1, 0);	break;
			case RIGHT: checkCrash(0, 1); 	break;
			case LEFT: 	checkCrash(0, -1);	break;
			case DOWN: 	checkCrash(1, 0);	break;
		}
		repaintSnake(snake);
	}
	
	public void checkCrash(int off_y, int off_x){
		int y = head.getY() + off_y;
		int x = head.getX() + off_x;
		
		if(x<0 || x>WIDTH-1 || y<0 || y>HEGHT-1 || gameRects[y][x].getFill()==Color.RED){
			timeline.stop();
			overPanel.setVisible(true);
			System.out.println("게임오버");
			return;
		}
		if(snakeRects[y][x].getFill()==Color.GREEN){
			snake.add(fruit);
			head =  fruit;
			return;
		}
		head = new Point(y, x);
		snake.add(head);
		snake.poll();
	}
	
	public void repaintSnake(LinkedList<Point> snake){
		for(int i=0; i<HEGHT; i++){
			for(int j=0; j<WIDTH; j++){
				snakeRects[i][j].setFill(Color.TRANSPARENT);
				if(fruit.getX()==j && fruit.getY()==i){
					//rectFill(snakeRects[j][i], Color.GREEN);
					snakeRects[j][i].setStyle("-fx-background-color: green;");
				}
			}
		}
		for(int i=0; i<snake.size(); i++){
			int y = snake.get(i).getY();
			int x = snake.get(i).getX();			
			rectFill(snakeRects[y][x], Color.web("#35b5cc"));
			snakeRects[y][x].setOpacity(0.8);
		}
	}
	
	public void initGameView(){
		
		//startPanel.setVisible(false);
		overPanel.setVisible(false);
		pausePanel.setVisible(false);
		
		Rectangle rect;
		gameRects = new Rectangle[HEGHT][WIDTH];
		snakeRects = new Rectangle[HEGHT][WIDTH];
		for(int i=0; i<HEGHT; i++){
			for(int j=0; j<WIDTH; j++){
				rect = new Rectangle(RECT_SIZE, RECT_SIZE);
				
				if((i+j)%2==0){
					rectFill(rect, Color.web("#192121"));
					rect.setOpacity(0.8);
				}
				else{
					rectFill(rect, Color.web("#192121"));
					rect.setOpacity(0.7);
				}				
				gameRects[i][j] = rect;
				gamePanel.add(rect, j, i);
				
				rect = new Rectangle(RECT_SIZE, RECT_SIZE);
				rectFill(rect, Color.TRANSPARENT);
				snakeRects[i][j] = rect;
				snakePanel.add(rect, j, i);
			}
		}
	}
	
	public void rectFill(Rectangle rect, Color color){
		rect.setFill(color);
		rect.setArcHeight(10);
		rect.setArcWidth(10);
	}

	public void polyFill(Polygon poly, Color color){
		poly.setFill(color);
		poly.setStroke(color);
		poly.setStrokeWidth(4);
		poly.setStrokeLineJoin(StrokeLineJoin.ROUND);		
	}
}


//Polygon polygon = new Polygon();
//polygon.getPoints().addAll(new Double[]{
//		    0.0, 0.0,
//		    10.0, 10.0,
//		    20.0, 0.0,
//		    20.0, 20.0,
//		    0.0, 20.0});
//fill(polygon);
//grid.add(polygon, 1, 1);
//
//
//polygon = new Polygon();
//polygon.getPoints().addAll(new Double[]{
//		    0.0, 0.0,
//		    0.0, 20.0,
//		    20.0, 20.0,
//		    20.0, 0.0 });
//fill(polygon);
//grid.add(polygon, 1, 2);
//
//polygon = new Polygon();
//polygon.getPoints().addAll(new Double[]{
//		    0.0, 0.0,
//		    0.0, 20.0,
//		    20.0, 20.0,
//		    20.0, 0.0 });
//fill(polygon);
//grid.add(polygon, 1, 3);
//
//polygon = new Polygon();
//polygon.getPoints().addAll(new Double[]{
//		    0.0, 0.0,
//		    20.0, 0.0,
//		    10.0, 20.0
//		    });
//fill(polygon);
//polygon.setRotate(90);
//grid.add(polygon, 1, 4);