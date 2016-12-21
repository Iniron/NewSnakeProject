package com.project.snake.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.project.snake.controller.GameController;
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
			//InetSocketAddress serverAdd = new InetSocketAddress("192.168.20.40", 8686);
			InetSocketAddress serverAdd = new InetSocketAddress("127.0.0.1", 8686);
			clientSocket.connect(serverAdd);
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("fail to connect server");
		}		
	}	
	
	public SnakeDTO sendData(SnakeDTO sendData){
		SnakeDTO recvData = null;
		try {
			//oos.reset();
			oos.writeObject(sendData);
			oos.flush();	
			recvData = (SnakeDTO)ois.readObject();
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("fail to send data");
		}
		return recvData;
	}
}
