package com.project.snake.database;

import java.io.Serializable;

public class SnakeDTO implements Serializable {
	String status;
	String id;
	String password;
	int t_score;
	int t_food;
	int t_level;
	int t_time;
	
	public SnakeDTO() {
	}
	
	public SnakeDTO(String status, String id, String password, int t_score, int t_food, int t_level, int t_time) {
		this.status = status;
		this.id = id;
		this.password = password;
		this.t_score = t_score;
		this.t_food = t_food;
		this.t_level = t_level;
		this.t_time = t_time;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getT_score() {
		return t_score;
	}

	public void setT_score(int t_score) {
		this.t_score = t_score;
	}

	public int getT_food() {
		return t_food;
	}

	public void setT_food(int t_food) {
		this.t_food = t_food;
	}

	public int getT_level() {
		return t_level;
	}

	public void setT_level(int t_level) {
		this.t_level = t_level;
	}

	public int getT_time() {
		return t_time;
	}

	public void setT_time(int t_time) {
		this.t_time = t_time;
	}
	
	
}
