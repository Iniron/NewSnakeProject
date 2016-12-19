package com.project.snake.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.project.snake.database.SnakeDAO;
import com.project.snake.database.SnakeDTO;

public class ServerThread extends Thread{
	
	SnakeDAO dao;
	Socket clientSocket;
	ObjectInputStream ois;
	ObjectOutputStream oos;
		
	public ServerThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			dao = new SnakeDAO();
			ois = new ObjectInputStream(clientSocket.getInputStream());
			oos = new ObjectOutputStream(clientSocket.getOutputStream());			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void run() {
		try {
			while(true){
				SnakeDTO data = (SnakeDTO)ois.readObject();
				
				switch(data.getStatus()){
					case "login": 
							data = dao.getMember(data.getId(), data.getPassword());
							break;
					case "join":
							data = dao.insertMember(data.getId(), data.getPassword());
							break;
					case "update": 
							data = dao.updateInfo(data.getId(), data.getT_score(), data.getT_food(), data.getT_level(), data.getT_time());
							break;
				}
				
				oos.writeObject(data);
				oos.flush();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(clientSocket.getInetAddress()+":disconnect");
		} finally {
			try {
				if(ois!=null) ois.close();
				if(clientSocket!=null)	clientSocket.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
