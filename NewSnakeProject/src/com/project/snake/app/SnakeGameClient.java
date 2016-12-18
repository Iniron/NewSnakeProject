package com.project.snake.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.project.snake.database.SnakeDTO;

public class SnakeGameClient {
	
	Socket clientSocket;
	InetSocketAddress serverAdd;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	
	GameController game_ctr;

	public SnakeGameClient(GameController game_ctr) {
		this.game_ctr = game_ctr;
		try {
			clientSocket = new Socket();
			InetSocketAddress serverAdd = new InetSocketAddress("127.0.0.1", 8686);
			clientSocket.connect(serverAdd);
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
		} catch (Exception e) {
			System.out.println("Invalid Server");
			e.printStackTrace();
		}		
	}	
	
	public SnakeDTO Login(SnakeDTO sendData){
		try {
			oos.reset();
			oos.writeObject(sendData);
			oos.flush();
			
			SnakeDTO recvData = (SnakeDTO)ois.readObject();
			return recvData;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public void Join(SnakeDTO data){
		try {
			oos.reset();
			oos.writeObject(data);
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}		 
	}
	
	public void ScoreUpdate(SnakeDTO data){
		try {
			oos.reset();
			oos.writeObject(data);
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}		 
	}
}