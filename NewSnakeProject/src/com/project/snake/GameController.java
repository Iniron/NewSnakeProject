package com.project.snake;

import javafx.scene.paint.Color;

public class GameController {
	
	ViewController view_ctr;
	SnakeController snake_ctr;
	
	public GameController(ViewController view_ctr) {
		this.view_ctr = view_ctr;						//ViewController의 객체를 인자로 받는다.
		snake_ctr = new SnakeController(this);				//SnakeController 객체 생성
		view_ctr.initGameView();					
	}
		
	public void gameStart(){
		snake_ctr.snakeCreate();
		snake_ctr.foodCreate();
		view_ctr.startView(snake_ctr.head, snake_ctr.tail, snake_ctr.food);
		view_ctr.setTimer();
	}
	
	public void changeDirection(Direction direction){
		switch (direction) {
			case UP: if(SnakeController.direction==Direction.DOWN) return; else break;
			case RIGHT: if(SnakeController.direction==Direction.LEFT) return; else break;
			case LEFT: if(SnakeController.direction==Direction.RIGHT) return; else break;
			case DOWN: if(SnakeController.direction==Direction.UP) return; else break;
		}
		SnakeController.direction = direction;
	}
	
	public void changeSnake(){
		switch(SnakeController.direction){
			case UP: 	checkCrash(-1, 0);	break;
			case RIGHT: checkCrash(0, 1); 	break;
			case LEFT: 	checkCrash(0, -1);	break;
			case DOWN: 	checkCrash(1, 0);	break;
		}
		view_ctr.repaintSnake(snake_ctr.snake, snake_ctr.bodyList, snake_ctr.head, snake_ctr.tail, snake_ctr.food);
	}
	
	public void checkCrash(int off_y, int off_x){
		int y = snake_ctr.head.getY() + off_y;
		int x = snake_ctr.head.getX() + off_x;
		
		if(x<0 || x>ViewController.WIDTH-1 || y<0 || y>ViewController.HEGHT-1 || view_ctr.snakeRects[y][x].getFill()==Color.web("#35b5cc")){
			view_ctr.gameOverView();
			return;
		}
		if(view_ctr.snakeRects[y][x].getFill()==snake_ctr.bodyList.get(snake_ctr.bodyList.size()-1)){
			snake_ctr.addHead();
			snake_ctr.foodCreate();
			view_ctr.foodCnt++;				//food를 먹으면 카운트 업
			view_ctr.bonusCnt = 101;		//food를 먹으면 점수가 100으로 초기화			
			return;
		}
		snake_ctr.move(y, x);
	}
	
}
