package com.project.snake.data;

import java.util.LinkedList;

import com.project.snake.controller.SnakeController;
import com.project.snake.controller.ViewController;

public class BombGenerator extends Thread {
	
	SnakeController snake_ctr;
	LinkedList<Point> bombs;
	Point bomb;
	int bombCnt;
	
	public BombGenerator(SnakeController snake_ctr, int bombCnt) {
		this.snake_ctr = snake_ctr;
		this.bombCnt = bombCnt;
		bombs = new LinkedList<>();
	}

	@Override
	public void run() {
		int i=0;
		while(i<bombCnt){
			bombCreator();
			i++;
		}
		snake_ctr.bombList = bombs;
		ViewController.isBomb = true;
	}
	
	//폭탄 생성 함수
	public void bombCreator(){
		while(true){
			int ranY = (int)(Math.random()*ViewController.HEGHT);
			int ranX = (int)(Math.random()*ViewController.WIDTH);		
			bomb = new Point(ranY, ranX);
			
			for(int i = 0; i < bombs.size(); i++) {				//리스트에 저장된 폭탄과의 중복방지
				int x = bombs.get(i).getX();
				int y = bombs.get(i).getY();
				if (x == ranX && y == ranY) continue;
			}
			for(int i = 0; i < snake_ctr.snake.size(); i++) {	//캐릭터의 위치와 폭탄의 중복방지
				int x = snake_ctr.snake.get(i).getX();
				int y = snake_ctr.snake.get(i).getY();
				if (x == ranX && y == ranY) continue;
			}
			if(snake_ctr.food.getX()==ranX && snake_ctr.food.getY()==ranY) continue;	//음식과 중복방지
			if(snake_ctr.head.getX()==ranX || snake_ctr.head.getY()==ranY) continue;	//캐릭터 진행방향과 중복방지 
				
			break;
		}
		bombs.add(bomb);
	}
}
