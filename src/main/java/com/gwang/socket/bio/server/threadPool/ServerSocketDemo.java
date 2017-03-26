package com.gwang.socket.bio.server.threadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwang.socket.bio.server.handler.SocketHandler;

/**
 * 线程池的服务器端
 * @author wanggang
 */
public class ServerSocketDemo {
	private static final Logger log = LoggerFactory.getLogger(ServerSocketDemo.class);
	
	private int port;
	
//	ExecutorService executor = Executors.newFixedThreadPool(4);
	ExecutorService executor = newBoundedFixedThreadPool(4, 16);
	
	public static ExecutorService newBoundedFixedThreadPool(int nThreads, int capacity) {
		return new ThreadPoolExecutor(nThreads, nThreads, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(
						capacity), new ThreadPoolExecutor.DiscardPolicy());
	}

	public ServerSocketDemo(int port) {
		this.port = port;
	}
	
	public void start() {
		init(port);
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
					executor.execute(new SocketHandler(socket));
//					executor.submit(new SocketHandler(socket));
				}
			} finally {
				if (listener != null) {
					listener.close();
					log.info("server is closed......");
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public static void main(String[] args) {
		ServerSocketDemo server = new ServerSocketDemo(9211);
		server.start();
	}
}
