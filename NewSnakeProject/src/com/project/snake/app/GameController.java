package com.project.snake.app;

import java.net.Socket;

import com.project.snake.database.SnakeDTO;

import javafx.scene.paint.Color;

public class GameController {
	
	ViewController view_ctr;
	SnakeController snake_ctr;
	SnakeGameClient network;
	Socket clientSocket;
	
	public GameController(ViewController view_ctr) {
		this.view_ctr = view_ctr;							//ViewController�� ��ü�� ���ڷ� �޴´�.
		snake_ctr = new SnakeController(this);				//SnakeController ��ü ����
		network = new SnakeGameClient(this);				//������ ����
		
		//SnakeDTO data = new SnakeDTO("abc", "123", 0, 0, 0, 0);
		//network.checkLogin(data);
		
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

		if(x<0 || x>ViewController.WIDTH-1 || y<0 || y>ViewController.HEGHT-1){			//���� ���� ���
			view_ctr.gameOverView();
			return;
		}		
		else if(snake_ctr.food.getY()==y && snake_ctr.food.getX()==x){					//food�� ���� ���
			snake_ctr.addHead();
			snake_ctr.foodCreate();
			view_ctr.foodCnt++;															//food ī��Ʈ ��
			view_ctr.scoreCnt += (50 * view_ctr.levelCnt) + view_ctr.bonusCnt;			//food ���� 50 + bonus ����
			if(view_ctr.scoreCnt >= 500 * (int)Math.pow(view_ctr.levelCnt+1, 2))			//level ����
				view_ctr.levelUp();
			//����		����		����				���ʽ�
			//0			1		50(50*level)	100(50*level+1)	
			//2000		2		100				150				
			//4500		3		150				200				
			//8000		4		200				250				
			//12500		5		250				300			
			view_ctr.bonusCnt = 50 * (view_ctr.levelCnt+1);								//������ 100���� �ʱ�ȭ	
			
			return;
		}else if(view_ctr.snakeRects[y][x].getFill()!=Color.TRANSPARENT){	//���� ��� ��쵵 �ƴϰ� ���� �ƴ϶�� body�浹
			view_ctr.gameOverView();
			return;
		}
		
		snake_ctr.move(y, x);
	}
	
	public void checkLogin(String id, String password){
		SnakeDTO data = new SnakeDTO("login", id, password, 0, 0, 0, 0);
		data = network.Login(data);
	}
	
	public void checkJoin(){
		
	}
	

	
}
