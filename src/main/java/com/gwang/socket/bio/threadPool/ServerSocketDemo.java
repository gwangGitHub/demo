package com.gwang.socket.bio.threadPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池的服务器端
 * @author wanggang
 */
public class ServerSocketDemo {
	private static final Logger log = LoggerFactory.getLogger(ServerSocketDemo.class);

	final static String RESPONSE = "HTTP/1.0 200 OK\r\n Content-type: text/plain\r\n\r\nHello World\r\n";
	
//	ExecutorService executor = Executors.newFixedThreadPool(4);
	ExecutorService executor = newBoundedFixedThreadPool(4, 16);
	
	public static ExecutorService newBoundedFixedThreadPool(int nThreads, int capacity) {
		return new ThreadPoolExecutor(nThreads, nThreads, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(
						capacity), new ThreadPoolExecutor.DiscardPolicy());
	}

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
					executor.submit(new HandleRequestRunnable(socket));
				}
			} finally {
				listener.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class HandleRequestRunnable implements Runnable {
		final Socket socket;

		public HandleRequestRunnable(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				handleRequest(socket);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
