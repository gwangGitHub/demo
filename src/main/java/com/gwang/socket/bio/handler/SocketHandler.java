package com.gwang.socket.bio.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketHandler implements Runnable {
	private final static Logger log = LoggerFactory.getLogger(SocketHandler.class);
	private final static String RESPONSE = "HTTP/1.0 200 OK\r\n Content-type: text/plain\r\n\r\nHello World\r\n";
	
	final Socket socket;

	public SocketHandler(Socket socket) {
		this.socket = socket;
	}

    public void run() {
		try {
			handleRequest(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleRequest(Socket socket) throws IOException {
		// Read the input stream, and return “200 OK”
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (true) {
				log.info(in.readLine());
				OutputStream out = socket.getOutputStream();
				out.write(RESPONSE.getBytes(StandardCharsets.UTF_8));
			}
		} finally {
			socket.close();
		}
	}
}
