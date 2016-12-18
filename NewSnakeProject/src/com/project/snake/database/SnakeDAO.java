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
				data.setT_food(resultSet.getInt("t_food"));
				data.setT_level(resultSet.getInt("t_level"));
				data.setT_score(resultSet.getInt("t_score"));
				data.setT_time(resultSet.getInt("t_time"));
			}else{
				data.setStatus("loginok");
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
	
	
	public void insertMember(){
		
	}
	
	public void updateInfo(){
		
	}

}
