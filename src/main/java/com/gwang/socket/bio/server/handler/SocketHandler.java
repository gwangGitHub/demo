package com.gwang.socket.bio.server.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketHandler implements Runnable {
	private final static Logger log = LoggerFactory.getLogger(SocketHandler.class);
	private final static String RESPONSE = "HTTP/1.0 200 OK\r\n Content-type: text/plain\r\n\r\nHello World\r\n";
	
	final Socket socket;

	public SocketHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
    public void run() {
		try {
			handleRequest(socket);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void handleRequest(Socket socket) throws IOException {
		// Read the input stream, and return “200 OK”
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			String body = null;
			while (true) {
				body = in.readLine();
				if (StringUtils.isBlank(body)) {
					//读取到""空字符串或是null标示读取内容完毕
					break;
				}
				log.info(body);
			}
			out.println(RESPONSE);
		} finally {
			if (in != null) {
				in.close();
				in = null;
			}
			if (out != null) {
				out.close();
				out = null;
			}
			if (socket != null) {
				socket.close();
				socket = null;
			}
		}
	}
}
