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
					case "login": login(); break;
					case "join": break;
					case "scoreupdate": break;
				}
				
				oos.writeObject(data);
				oos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ois!=null) ois.close();
				if(clientSocket!=null)	clientSocket.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	private void login(){
	}

}
