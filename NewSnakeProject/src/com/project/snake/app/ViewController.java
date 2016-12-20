package com.project.snake.app;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.project.snake.database.SnakeDTO;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ViewController implements Initializable {
	
	public static final int HEGHT = 30;
	public static final int WIDTH = 30;
	public static final int RECT_SIZE = 25;
	public static boolean isBomb = false;
	
	//---------- Pane ----------
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
	private HBox startPanel;
	
	//---------- TextField ----------
	@FXML
	private TextField loginIdText;
	@FXML
	private PasswordField loginPwText;
	@FXML
	private TextField joinIdText;
	@FXML
	private PasswordField joinPwText;
	@FXML
	private PasswordField joinPwCheckText;
	
	//---------- Label ----------
	@FXML
	private Label highScoreLabel;
	@FXML
	private Label idLabel;
	@FXML
	private Label scoreLabel;
	@FXML
	private Label bonusLabel;
	@FXML
	private Label pauseLabel;
	@FXML
	private Label quitLabel;
	@FXML
	private Label foodLabel;
	@FXML
	private Label levelLabel;
	@FXML
	private Label timeLabel;
	@FXML
	private Label loginLabel;
	@FXML
	private Label joinLabel;
	@FXML
	private Label loginAlert;	
	@FXML
	private Label yesLabel;
	@FXML
	private Label noLabel;
	@FXML
	private Label joinAlert;
	@FXML
	private Label overScoreLabel;
	@FXML
	private Label overFoodLabel;
	@FXML
	private Label overLevelLabel;
	@FXML
	private Label overTimeLabel;
	@FXML
	private Label overRestartLabel;
	@FXML
	private Label overQuitLabel;
	@FXML
	private Label pauseResumeLabel;	

	GameController game_ctr;
	Stage primaryStage;
	Rectangle[][] gameRects;
	Rectangle[][] snakeRects;
	
	Timeline runThread;
	Timeline timeThread;
	int runSpeed;
	int timeSpeed;
	double levelSpeed;
	
	public int bonusCnt;
	public int foodCnt;
	public int scoreCnt;
	public int levelCnt;
	public int timeCnt;
	
	boolean isKey = false;
	boolean isStart = false;
	boolean isDirection = false;
	
	SnakeDTO member = new SnakeDTO();
	Image haedImage = new Image("head.png");
	Image tailImage = new Image("tail.png");
	Image bombRImage = new Image("bomb_red.png");
	Image bombGImage = new Image("bomb.png");
	ImagePattern haedImgPtn = new ImagePattern(haedImage);	
	ImagePattern tailImgPtn = new ImagePattern(tailImage);
	ImagePattern bombRImgPtn = new ImagePattern(bombRImage);
	ImagePattern bombGImgPtn = new ImagePattern(bombGImage);
	ImagePattern bombColor[] = {bombRImgPtn, bombGImgPtn};
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Font.loadFont(getClass().getResource("04B_19__.ttf").toExternalForm(), 38);
		game_ctr = new GameController(this);
		//bonusLabel.textProperty().bind(arg0);
		
		gamePanel.setFocusTraversable(true);
		gamePanel.requestFocus();
		gamePanel.setOnKeyPressed(event->{
			if(isKey){
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
					startPanel.setVisible(false);
				}
				if(event.getCode() == KeyCode.SPACE){
					runThread.setRate(levelCnt+2);
				}
				if(event.getCode() == KeyCode.P){
					gamePauseView();
				}
			}
		});
		gamePanel.setOnKeyReleased(event->{
			if(event.getCode() == KeyCode.SPACE){
				runThread.setRate(levelCnt);
			}
		});
		pauseLabel.setOnMouseClicked(event->{	if(isStart) gamePauseView(); });
		quitLabel.setOnMouseClicked(event->{ startPanel.setVisible(false); overPanel.setVisible(false); loginPanel.setVisible(true); game_ctr.quitGame(); });
		
		loginLabel.setOnMouseClicked(event->{	loginAlert.setText(null) ;game_ctr.checkLogin(loginIdText.getText(), loginPwText.getText());	});
		joinLabel.setOnMouseClicked(event->{	loginPanel.setVisible(false);	joinPanel.setVisible(true);	loginAlert.setText(null);	});
		yesLabel.setOnMouseClicked(event->{	joinAlert.setText(null); game_ctr.checkJoin(joinIdText.getText(), joinPwText.getText(),  joinPwCheckText.getText());	});
		noLabel.setOnMouseClicked(event->{	loginPanel.setVisible(true);	joinPanel.setVisible(false);	joinAlert.setText(null);	});
		
		overRestartLabel.setOnMouseClicked(event->{	overPanel.setVisible(false); startPanel.setVisible(true); game_ctr.restart(); });
		overQuitLabel.setOnMouseClicked(event->{ overPanel.setVisible(false); loginPanel.setVisible(true); game_ctr.quitGame();	});
		
		pauseResumeLabel.setOnMouseClicked(event->{	gamePauseView(); });
	}
	
	public void repaintSnake(LinkedList<Point> snake, LinkedList<Paint> bodyList, LinkedList<Point> bombList, Point head, Point tail, Point food){
		refreshSnakePanel();
		snakeRects[food.getY()][food.getX()].setFill(bodyList.get(bodyList.size()-1));
		if(isBomb) repaintBomb(bombList);
		
		for(int i=0; i<snake.size(); i++){
			int y = snake.get(i).getY();
			int x = snake.get(i).getX();			
			if(i==snake.size()-1)
				repaintHead(y, x);
			else if(i==0)
				repaintTail(y, x, snake);
			else {
				rectFill(snakeRects[y][x], bodyList.get(bodyList.size()-1-i));
			}
		}
	}
	
	public void repaintHead(int y, int x){
		snakeRects[y][x].setFill(haedImgPtn);
		if(SnakeController.direction == Direction.RIGHT) 	 snakeRects[y][x].setRotate(90);
		else if(SnakeController.direction == Direction.DOWN) snakeRects[y][x].setRotate(180);
		else if(SnakeController.direction == Direction.LEFT) snakeRects[y][x].setRotate(270);
		else												 snakeRects[y][x].setRotate(0);
	}
	
	public void repaintTail(int y, int x, LinkedList<Point> snake){
		snakeRects[y][x].setFill(tailImgPtn);
		int front_x = snake.get(1).getX();
		int front_y = snake.get(1).getY();
		
		if(front_x > x) 	 snakeRects[y][x].setRotate(90);
		else if(front_x < x) snakeRects[y][x].setRotate(270);
		else if(front_y > y) snakeRects[y][x].setRotate(180);
		else				 snakeRects[y][x].setRotate(0);
	}
	
	public void repaintBomb(LinkedList<Point> bombList){
		for(int i=0; i<bombList.size(); i++){
			int y = bombList.get(i).getY();
			int x = bombList.get(i).getX();	
			snakeRects[y][x].setFill(bombColor[(int)(Math.random()*2)]);
			snakeRects[y][x].setRotate(0);
			snakeRects[y][x].setOpacity(0.8);
		}
	}
	
	public void refreshSnakePanel(){
		for(int i=0; i<HEGHT; i++){
			for(int j=0; j<WIDTH; j++){
				snakeRects[i][j].setFill(Color.TRANSPARENT);
			}
		}
	}
	
	public void initGameView(){
		
		//loginPanel.setVisible(false);
		joinPanel.setVisible(false);
		overPanel.setVisible(false);
		pausePanel.setVisible(false);
		startPanel.setVisible(false);
		
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
	
	public void startView(Point head, Point tail, Point food){

		loginPanel.setVisible(false);
		isStart = true;
		
		int y = head.getY();							
		int x = head.getX();					//헤드를 인자로 받아 좌표검색		
		snakeRects[y][x].setFill(haedImgPtn); 	//좌표에 헤드를 위치한다.
		y = tail.getY();
		x = tail.getX();
		snakeRects[y][x].setFill(tailImgPtn); 	//좌표에 꼬리를 위치한다.
		
		//---------------------------------
		//각종 초기화 변수들
		scoreCnt = 0;
		bonusCnt = 100;
		foodCnt = 0;
		levelCnt = 1;
		timeCnt = 0;
		
		runSpeed = 100;
		timeSpeed = 100;
		levelSpeed = 1.0;
		//---------------------------------
		
		if(runThread==null || runThread.getStatus().equals(Status.STOPPED)){
			runThread = new Timeline(new KeyFrame(Duration.millis(runSpeed), event -> game_ctr.changeSnake()));
			runThread.setCycleCount(Timeline.INDEFINITE);
			runThread.play();
		}
	}
	
	public void gameOverView(){
		isKey = false;
		isStart = false;
		runThread.stop();
		timeThread.stop();
		overPanel.setVisible(true);
		
		overScoreLabel.setText(Integer.toString(scoreCnt));
		overFoodLabel.setText(Integer.toString(foodCnt));
		overLevelLabel.setText(Integer.toString(levelCnt));
		int sec = (timeCnt / 10) % 60;
		int min = (timeCnt / 10 / 60) % 60;
		String DurationTime = String.format("%02d:%02d", min, sec);
		overTimeLabel.setText(DurationTime);
	}
	
	public void gamePauseView(){
//		if(pausePanel.isVisible()) 	pausePanel.setVisible(false);
//		else						pausePanel.setVisible(true);

		if(runThread.getStatus()==Status.RUNNING){
			runThread.pause();
			timeThread.pause();
		}
		else if(runThread.getStatus()==Status.PAUSED){
			runThread.play();
			timeThread.play();
		}
	}
	
	public void rectFill(Rectangle rect, Paint color){
		rect.setFill(color);
		rect.setArcHeight(10);
		rect.setArcWidth(10);
	}
	
	public void updateTrigger() {
		if (bonusCnt > 10) {
			bonusCnt--;
		}
		timeCnt++;
		int sec = (timeCnt / 10) % 60;
		int min = (timeCnt / 10 / 60) % 60;
		String DurationTime = String.format("%02d:%02d", min, sec);
		
		if(timeCnt%100==0){
			game_ctr.updateBomb(1, 10+levelCnt*5);
		}else if(timeCnt%50==0){
			game_ctr.updateBomb(0, 0);
		}
		
		scoreLabel.setText(Integer.toString(scoreCnt));
		foodLabel.setText(Integer.toString(foodCnt));
		bonusLabel.setText(Integer.toString(bonusCnt));
		timeLabel.setText(DurationTime);
	}
	
	public void setTimer(){
		if (timeThread == null || timeThread.getStatus().equals(Status.STOPPED)) {
			timeThread = new Timeline();
			KeyFrame k = new KeyFrame(Duration.millis(timeSpeed), event -> updateTrigger());
			timeThread.getKeyFrames().add(k);
			timeThread.setCycleCount(Timeline.INDEFINITE);
			timeThread.play();
		}
	}
	
	public void levelUp(){
		levelCnt++;
		levelLabel.setText(Integer.toString(levelCnt));
		levelSpeed = levelSpeed + 0.15;
		runThread.setRate(levelSpeed);
	}
	
	public void loginView(String status){
		if(status.equals("loginok")){
			isKey = true;
			loginPanel.setVisible(false);
			startPanel.setVisible(true);
			refreshSidePanel();
		}		
		else if(status.equals("loginno"))
			loginAlert.setText("Login Failed");
	}
	
	public void joinView(String status){
		if(status.equals("joinok")){
			joinPanel.setVisible(false);
			loginPanel.setVisible(true);
			loginAlert.setText("Join Success");
		}else if(status.equals("joinno")){
			joinAlert.setText("Join Failed");
		}
	}
	
	public void quitView(String status){
		refreshSidePanel();
	}
	
	public void refreshSidePanel(){
		highScoreLabel.setText(Integer.toString(member.getT_score()));
		idLabel.setText(member.getId());
		scoreLabel.setText("0");
		bonusLabel.setText("100");
		foodLabel.setText("0");
		levelLabel.setText("1");
		timeLabel.setText("00:00");
	}
// 	키바꾸는 로직
//	if(event.getCode() == KeyCode.T){
//		turnKey();
//	}
//	public void turnKey(){
//		Direction key1 = Direction.UP;
//		Direction key2 = Direction.RIGHT;
//		Direction key3 = Direction.LEFT;
//		Direction key4 = Direction.DOWN;
//		
//		if(key1==Direction.UP){
//		key1 = Direction.DOWN;
//		key2 = Direction.LEFT;
//		key3 = Direction.RIGHT;
//		key4 = Direction.UP;
//		}else{
//		key1 = Direction.UP;
//		key2 = Direction.RIGHT;
//		key3 = Direction.LEFT;
//		key4 = Direction.DOWN;
//		}
//	}
}
