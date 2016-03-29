package com.gwang.socket.bio.client;

import java.net.Socket;

public class SocketDemo {
	
	private String address;
	private int port;
	
	public SocketDemo(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public void connect() throws Exception{
		Socket socket = new Socket(address, port);
		this.sent(socket);
		this.receive(socket);
	}
	
	private void sent(Socket socket) {
		SendThread sendThread = new SendThread(socket);
		new Thread(sendThread).start();
	}
	
	private void receive (Socket socket) {
		ReceiveThread receiveThread = new ReceiveThread(socket);
		new Thread(receiveThread).start();
	}
	
	public static void main(String[] args) throws Exception{
		SocketDemo sock = new SocketDemo("localhost", 9211);
		sock.connect();
	}
}
