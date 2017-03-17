package com.gwang.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;

public class SocketChannelDemo {
	private Selector selector; //选择器
	private SelectorHandler handler;
    
    public SocketChannelDemo (String host, int port) {
    	init(host, port);
    }
    
    //初始化客户端     
    public void init(String host, int port) {  
    	try {
	        // 打开选择器  
	        selector = SelectorProvider.provider().openSelector();
            handler = new SelectorHandler(selector);
            // 打开socket通道  
            SocketChannel socketChannel = SocketChannel.open();  
            // 设置为非阻塞方式  
            socketChannel.configureBlocking(false);  
	        // 注册连接服务端socket动作  
	        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);  
	        // 连接  
	        socketChannel.connect(new InetSocketAddress(host, port));  
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
	 * 监听选择器通知
	 */
	public void listen () {
		handler.listen();
	}
  
    public static void main(String[] args) throws IOException {  
        new SocketChannelDemo("localhost", 8099).listen();  
    }  
}
