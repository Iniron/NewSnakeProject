package com.project.snake.app;

public class Point {
	int y;
	int x;
	
	public Point(int y, int x) {
		this.y = y;
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void move(int y, int x){
		this.y += y;
		this.x += x;
	}

}
