package com.gwang.socket.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectorHandler {
	
	private Selector selector; //选择器
    /*缓冲区大小*/  
    private int BLOCK = 4096;  
    /*接受数据缓冲区*/  
    private ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);  
    /*发送数据缓冲区*/  
    private ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);  
	
	public SelectorHandler (Selector selector) {
		this.selector = selector;
	}
	
	/**
	 * 监听选择器通知
	 */
	public void listen () {
		while (true) {  
            try {  
                System.out.println("listen ... ");  
                //选择一组键，其相应的通道已为 I/O 操作准备就绪。     
                this.selector.select();
  
                //返回此选择器的已选择键集     
                Iterator<SelectionKey> selectorKeys = this.selector.selectedKeys().iterator();  
                while (selectorKeys.hasNext()) {  
                    //这里找到当前的选择键     
                    SelectionKey key = selectorKeys.next();  
                    System.out.println("key ... " + key);  
                    //然后将它从返回键队列中删除     
                    selectorKeys.remove();  
                    if (!key.isValid()) { // 选择键无效  
                        continue;  
                    }
                    this.handleKey(key);
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
	}
	
	private void handleKey (SelectionKey key) throws IOException{
		if (key.isConnectable()) {  
            //链接服务端成功     
            this.connect(key);  
        } else if (key.isAcceptable()) {  
            //如果遇到客户端请求那么就响应     
            this.accept(key);  
        } else if (key.isReadable()) {  
            //读取客户端的数据     
            this.read(key);  
        } else if (key.isWritable()) {  
            //向客户端写数据     
            this.write(key);  
        } 
	}
	
	private void connect (SelectionKey key) throws IOException {
		System.out.println("client connect");  
		SocketChannel client = (SocketChannel) key.channel();  
        // 判断此通道上是否正在进行连接操作。  
        // 完成套接字通道的连接过程。  
        if (client.isConnectionPending()) {  
            client.finishConnect();  
            System.out.println("完成连接!");  
            this.writeText(client, "Hello,Server");
        }  
        client.register(selector, SelectionKey.OP_READ);  
	}
	
	/**   
     * 这个方法是连接   
     * 客户端连接服务器   
     * @throws IOException    
     * */  
	private void accept(SelectionKey key) throws IOException {  
        ServerSocketChannel server = (ServerSocketChannel) key.channel();  
        // 接受到此通道套接字的连接。  
        // 此方法返回的套接字通道（如果有）将处于阻塞模式。 
        SocketChannel client = server.accept();  
        // 配置为非阻塞 
        client.configureBlocking(false);  
        //OP_READ用于读取操作的操作集位     
        client.register(this.selector, SelectionKey.OP_READ);  
    }  
  
    /**   
     * 从通道中读取数据   
     * 并且判断是给那个服务通道的   
     * @throws IOException    
     * */  
	private void read(SelectionKey key) throws IOException {  
        //通过选择键来找到之前注册的通道     
        SocketChannel client = (SocketChannel) key.channel();  
        //将缓冲区清空以备下次读取 
        this.receivebuffer.clear();  
        //从通道里面读取数据到缓冲区并返回读取字节数     
        int count = client.read(this.receivebuffer);  
//        if (count == -1) {  
//            //取消这个通道的注册     
//            key.channel().close();  
//            key.cancel();  
//            return;  
//        } else 
        if (count > 0) {  
        	//将数据从缓冲区中拿出来     
        	String receiveText = new String(this.receivebuffer.array(), 0, count);  
            System.out.println("服务器端接受客户端数据--:" + receiveText);  
            client.register(selector, SelectionKey.OP_WRITE);
        } 
    }  
    
	private void write(SelectionKey key) throws IOException {  
        // 返回为之创建此键的通道。  
        SocketChannel client = (SocketChannel) key.channel();  
        String sendText = "message from server--";
        this.writeText(client, sendText);
        client.register(selector, SelectionKey.OP_READ);  
    }
	
	private void writeText(SocketChannel client, String sendText) throws IOException {
		//将缓冲区清空以备下次写入  
        this.sendbuffer.clear();  
        //向缓冲区中输入数据  
        this.sendbuffer.put(sendText.getBytes());  
        //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
        this.sendbuffer.flip();  
        //输出到通道  
        client.write(this.sendbuffer);  
        System.out.println("服务器端向客户端发送数据--：" + sendText);  
	}
}
