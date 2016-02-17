package com.gwang.socket.bio.singleThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单线程的服务器端
 * @author wanggang
 */
public class ServerSocketDemo {
    private static final Logger log = LoggerFactory.getLogger(ServerSocketDemo.class);
	
	final static String RESPONSE = "HTTP/1.0 200 OK\r\n Content-type: text/plain\r\n\r\nHello World\r\n";

	public ServerSocketDemo(int port) {
		this.init(port);
	}

	/**
	 * 初始化服务端
	 * 
	 * @param port
	 */
	public void init(int port) {
		try {
			ServerSocket listener = new ServerSocket(port);
			try {
				while (true) {
					Socket socket = listener.accept();
					try {
						handleRequest(socket);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} finally {
				listener.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void handleRequest(Socket socket) throws IOException {
		// Read the input stream, and return “200 OK”
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			log.info(in.readLine());
			OutputStream out = socket.getOutputStream();
			out.write(RESPONSE.getBytes(StandardCharsets.UTF_8));
		} finally {
			socket.close();
		}
	}
}
