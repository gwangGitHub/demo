package com.gwang.socket.bio.server.multiThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwang.socket.bio.server.handler.SocketHandler;

/**
 * 多线程的服务器端
 * @author wanggang
 */
public class ServerSocketDemo {
	private static final Logger log = LoggerFactory.getLogger(ServerSocketDemo.class);

	private int port;

	public ServerSocketDemo(int port) {
		this.port = port;
	}
	
	public void start () {
		this.init(port);
	}

	/**
	 * 初始化服务端
	 * 
	 * @param port
	 */
	private void init(int port) {
		try {
			ServerSocket listener = new ServerSocket(port);
			log.info("server start......");
			try {
				while (true) {
					Socket socket = listener.accept();
					log.info("socket accept...... address:{}", socket.getLocalAddress());
					new Thread(new SocketHandler(socket)).start();
				}
			} finally {
				listener.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ServerSocketDemo demo = new ServerSocketDemo(9211);
		demo.start();
	}
}
