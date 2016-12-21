package com.project.snake.controller;

import java.util.LinkedList;

import com.project.snake.data.BombGenerator;
import com.project.snake.data.ColorGenerator;
import com.project.snake.data.Direction;
import com.project.snake.data.Point;

import javafx.scene.paint.Paint;

public class SnakeController {
	
	public static Direction direction;
	
	ColorGenerator colorGenerator;
	BombGenerator bombGenerator;
	public LinkedList<Point> snake;
	public LinkedList<Point> bombList;
	public LinkedList<Paint> bodyList;
	GameController game_ctr;
	public Point head;
	public Point tail;
	public Point food;
	
	public SnakeController(GameController game_ctr) {
		this.game_ctr = game_ctr; 
		colorGenerator = new ColorGenerator(); 
		snake =  new LinkedList<>();
		bodyList =  new LinkedList<>();
		bombList = new LinkedList<>();
		colorGenerator.ColorCreate();
	}
	
	//캐릭터의 Head와 Tail초기 설정
	public void snakeCreate(){
		head = new Point(ViewController.HEGHT/2, ViewController.WIDTH/2);		
		snake.add(head);
		direction = Direction.UP;
		
		tail = new Point(head.getY()+1, head.getX());
		snake.addFirst(tail);
	}
	//음식 랜덤 생성
	public void foodCreate(){
		int ranY = (int)(Math.random()*ViewController.HEGHT);
		int ranX = (int)(Math.random()*ViewController.WIDTH);		
		food = new Point(ranY, ranX);
		
		for(int i = 0; i < bombList.size(); i++) {
			int x = bombList.get(i).getX();
			int y = bombList.get(i).getY();
			if (x == ranX && y == ranY) {
				foodCreate(); 
				return;
			}
		}
		for(int i = 0; i < snake.size(); i++) {
			int x = snake.get(i).getX();
			int y = snake.get(i).getY();
			if (x == ranX && y == ranY) {
				foodCreate(); 
				return;
			}
		}
		bodyList.add(colorGenerator.getRandomColor());
	}
	//폭탄 생성 스레드 호출
	public void bombCreate(int bombCnt){
		bombGenerator = new BombGenerator(this, bombCnt);
		bombGenerator.start();
	}
	//Head 추가
	public void addHead(){
		snake.add(food);
		head = food;
	}
	//캐릭터 이동
	public void move(int y, int x){
		head = new Point(y, x);
		snake.add(head);
		snake.poll();
		tail = snake.peek();
	}
	
}
