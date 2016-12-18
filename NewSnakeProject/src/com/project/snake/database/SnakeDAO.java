package com.project.snake.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SnakeDAO {
	
	Connection connection;
	
	public SnakeDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String uid = "hr";
			String upw = "hr";
			connection = DriverManager.getConnection(url, uid, upw);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public SnakeDTO getMember(String id, String password){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;;
		SnakeDTO data = null;
		String sql = "select * from snake_members where id = ? and password= ? ";
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			data = new SnakeDTO();
			
			if(resultSet.next()){
				data.setStatus("loginok");
				data.setId(resultSet.getString("id"));
				data.setPassword(resultSet.getString("password"));
				data.setT_score(resultSet.getInt("t_score"));
				data.setT_food(resultSet.getInt("t_food"));
				data.setT_level(resultSet.getInt("t_level"));				
				data.setT_time(resultSet.getInt("t_time"));
			}else{
				data.setStatus("loginno");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement!=null) preparedStatement.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return data;
	}
	
	
	public SnakeDTO insertMember(String id, String password){
		PreparedStatement preparedStatement = null;
		SnakeDTO data = null;
		String sql = "insert into snake_members values (?, ?, default, default, default, default)";
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, password);
			data = new SnakeDTO();
			
			if(preparedStatement.executeUpdate()==1)	data.setStatus("joinok");
			else										data.setStatus("joinno");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement!=null) preparedStatement.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return data;
	}
	
	public SnakeDTO updateInfo(String id, int t_score, int t_food, int t_level, int t_time){
		PreparedStatement preparedStatement = null;
		SnakeDTO data = null;
		String sql = "update snake_members set t_score=?, t_food=?, t_level=?, t_time=? where id=?";
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, t_score);
			preparedStatement.setInt(2, t_food);
			preparedStatement.setInt(3, t_level);
			preparedStatement.setInt(4, t_time);
			preparedStatement.setString(5, id);
			data = new SnakeDTO();
			
			if(preparedStatement.executeUpdate()==1)	data.setStatus("updateok");
			else										data.setStatus("updateno");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement!=null) preparedStatement.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return data;
	}

}
