package com.project.snake;

import java.util.LinkedList;

public class SnakeController {
	
	
	LinkedList<Point> snake =  new LinkedList<>();
	GameController game_ctr;
	Point head;
	Point tail;
	Point food;
	Direction direction;
	
	public SnakeController(GameController game_ctr) {
		this.game_ctr = game_ctr; 
	}
	
	public void headCreate(){
		head = new Point(ViewController.HEGHT/2, ViewController.WIDTH/2);		
		snake.add(head);
		direction = Direction.UP;
	}
	
	public void foodCreate(){
		food = new Point(2, 2);
		//랜덤 생성 로직
	}
	
	public void addHead(){
		snake.add(food);
		head = food;
	}
	
	public void move(int y, int x){
		head = new Point(y, x);
		snake.add(head);
		snake.poll();
	}
}
