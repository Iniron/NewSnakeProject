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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ViewController implements Initializable {
	
	public static final int HEGHT = 30;
	public static final int WIDTH = 30;
	public static final int RECT_SIZE = 25;
	
	@FXML
	private GridPane gamePanel;
	
	@FXML
	private GridPane snakePanel;
	
	@FXML
	private HBox loginPanel;
	
	@FXML
	private HBox joinPanel;

	@FXML
	private HBox pausePanel;
	
	@FXML
	private HBox overPanel;
	
	@FXML
	private Label score;	
	
	@FXML
	private TextField loginIdText;
	
	Stage primaryStage;
	Rectangle[][] gameRects;
	Rectangle[][] snakeRects;
	
	Timeline timeline;
	int timeSpeed = 100;
	GameController game_ctr;
	
	public void setPrimaryStage(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Font.loadFont(getClass().getResource("04B_19__.ttf").toExternalForm(), 38);
		game_ctr = new GameController(this);				
				
		gamePanel.setFocusTraversable(true);
		gamePanel.requestFocus();
		gamePanel.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.UP){
				game_ctr.changeDirection(Direction.UP);
				event.consume();
			}
			if(event.getCode() == KeyCode.RIGHT){
				game_ctr.changeDirection(Direction.RIGHT);
				event.consume();
			}
			if(event.getCode() == KeyCode.LEFT){
				game_ctr.changeDirection(Direction.LEFT);
				event.consume();
			}
			if(event.getCode() == KeyCode.DOWN){
				game_ctr.changeDirection(Direction.DOWN);
				event.consume();
			}
			if(event.getCode() == KeyCode.ENTER){
				game_ctr.gameStart();
			}
			if(event.getCode() == KeyCode.SPACE){
				//timeline.setDelay(Duration.millis(1000));
			}
			if(event.getCode() == KeyCode.P){
				gamePauseView();
			}
		});
	}
	
	public void repaintSnake(LinkedList<Point> snake, Point food){
		for(int i=0; i<HEGHT; i++){
			for(int j=0; j<WIDTH; j++){
				snakeRects[i][j].setFill(Color.TRANSPARENT);
				if(food.getX()==j && food.getY()==i){
					rectFill(snakeRects[j][i], Color.GREEN);
					//snakeRects[j][i].setStyle("-fx-background-color: green;");
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
		
		//loginPanel.setVisible(false);
		joinPanel.setVisible(false);
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
	
	public void startView(Point head, Point food){
		
		loginPanel.setVisible(false);
		
		int y = head.getY();							
		int x = head.getX();							//헤드를 인자로 받아 좌표검색		
		snakeRects[y][x].setFill(Color.web("#35b5cc")); //좌표에 헤드를 위치한다.
		
		y = food.getY();
		x = food.getX();		
		snakeRects[y][x].setFill(Color.GREEN);
		snakeRects[y][x].setStyle("-fx-background-color:white;");	//food를 위치한다.
		
		//---------------------------------
		//각종 시간과 점수 초기화 코드 작성
		//---------------------------------
				
		if(timeline==null){
			timeline = new Timeline(new KeyFrame(Duration.millis(timeSpeed), event -> game_ctr.changeSnake()));
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();
		}
	}
	
	public void gameOverView(){
		timeline.stop();
		overPanel.setVisible(true);
		System.out.println("게임오버");
	}
	
	public void gamePauseView(){
		if(pausePanel.isVisible()) 	pausePanel.setVisible(false);
		else						pausePanel.setVisible(true);

		if(timeline.getStatus()==Status.RUNNING)
		timeline.pause();
		else if(timeline.getStatus()==Status.PAUSED)
		timeline.play();
	}
	
	public void rectFill(Rectangle rect, Color color){
		rect.setFill(color);
		rect.setArcHeight(10);
		rect.setArcWidth(10);
	}
}

//#3bbdc4 / 60, 187, 194 판넬파란색
//#2cb8d1 / 44, 184, 209 하늘색
//#f2b233 / 240, 174, 53 주황색
//#8342bd / 129, 66, 189 보라색
//#e388cc / 227, 136, 203 분홍색
//#ed6051 / 235, 95, 82 빨간색
//#2eb094 / 48, 176, 148 초록색