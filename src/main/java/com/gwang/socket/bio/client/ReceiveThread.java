package com.gwang.socket.bio.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiveThread implements Runnable{
	private static final Logger log = LoggerFactory.getLogger(ReceiveThread.class);
	private Socket socket;
	
	public ReceiveThread (Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (true)
			{
				String str = in.readLine();
				log.info("receive:{}", str);
				if (str.equals("byebye"))
				{
					break;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
