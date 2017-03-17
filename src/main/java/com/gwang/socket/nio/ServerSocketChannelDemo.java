package com.gwang.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerSocketChannelDemo {
	private static final Logger log = LoggerFactory.getLogger(ServerSocketChannelDemo.class);
	
	private Selector selector; //选择器
	private SelectorHandler handler;

	public ServerSocketChannelDemo (int port) {
		this.init(port);
	}
	
	/**
	 * 初始化服务端
	 * @param port
	 */
	public void init (int port) {
		try {
            //创建选择器     
            this.selector = SelectorProvider.provider().openSelector();
            handler = new SelectorHandler(selector);
            //打开服务器通道  
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			//告诉程序现在不是阻塞方式的     
            serverSocketChannel.configureBlocking(false);  
            //获取现在与该通道关联的套接字  
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(port);
            serverSocket.bind(address);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			log.info("Server Start----port:" + port);  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listen () {
		handler.listen();
	}
	
	public void stop () {
		handler.stop();
	}
	
    public static void main(String[] args) throws IOException {  
        new ServerSocketChannelDemo(8099).listen();  
    }  
}
