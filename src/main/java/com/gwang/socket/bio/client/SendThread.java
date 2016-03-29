package com.gwang.socket.bio.client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendThread implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(SendThread.class);
	private Socket socket;
	
	public SendThread (Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream());    
			Scanner scanner = new Scanner(System.in);
			log.info("please enter:");
			while (true) {
				String str = scanner.nextLine();
				log.info("enter:{}", str);
				if ("bye".equals(str)) {
					break;
				}
				out.println(str);
				out.flush();
			}
			scanner.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
