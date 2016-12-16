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
		snake_ctr.headCreate();
		snake_ctr.foodCreate();
		view_ctr.startView(snake_ctr.head, snake_ctr.food);
	}
	
	public void changeDirection(Direction direction){
		switch (direction) {
			case UP: if(direction.equals(Direction.DOWN)) return; else break;
			case RIGHT: if(direction.equals(Direction.LEFT)) return; else break;
			case LEFT: if(direction.equals(Direction.RIGHT)) return; else break;
			case DOWN: if(direction.equals(Direction.UP)) return; else break;
		}
		snake_ctr.direction = direction;
	}
	
	public void changeSnake(){
		switch(snake_ctr.direction){
			case UP: 	checkCrash(-1, 0);	break;
			case RIGHT: checkCrash(0, 1); 	break;
			case LEFT: 	checkCrash(0, -1);	break;
			case DOWN: 	checkCrash(1, 0);	break;
		}
		view_ctr.repaintSnake(snake_ctr.snake, snake_ctr.food);
	}
	
	public void checkCrash(int off_y, int off_x){
		int y = snake_ctr.head.getY() + off_y;
		int x = snake_ctr.head.getX() + off_x;
		
		if(x<0 || x>ViewController.WIDTH-1 || y<0 || y>ViewController.HEGHT-1 || view_ctr.snakeRects[y][x].getFill()==Color.web("#35b5cc")){
			view_ctr.gameOverView();
			return;
		}
		if(view_ctr.snakeRects[y][x].getFill()==Color.GREEN){
			snake_ctr.addHead();
			return;
		}
		snake_ctr.move(y, x);
	}
	
}
