package com.project.snake.server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SnakeGameServer {

	public static void main(String[] args) {
	
		ServerSocket serverSocket = null;
		InetSocketAddress serverAdd = null;;
		Socket clientSocket = null;;
		
		try {
			// 서버용 소켓 생성
			serverSocket = new ServerSocket();								
			//serverAdd = new InetSocketAddress("192.168.20.40", 8686);
			serverAdd = new InetSocketAddress("127.0.0.1", 8686);
			serverSocket.bind(serverAdd);
			
			// 클라이언트의 접속 대기
			while(true){
				clientSocket = serverSocket.accept();							//클라이언트가 접속되면
				ServerThread serverThread = new ServerThread(clientSocket);		//스레드를 생성해서
				serverThread.start();											//호출
				System.out.println(clientSocket.getInetAddress()+":connect");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(clientSocket!=null) clientSocket.close();
				if(serverSocket!=null) serverSocket.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
