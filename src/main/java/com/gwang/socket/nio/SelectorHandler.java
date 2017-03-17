package com.gwang.socket.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectorHandler {
	private static final Logger log = LoggerFactory.getLogger(SelectorHandler.class);
	
	private Selector selector; //选择器
    /*缓冲区大小*/  
    private int BLOCK = 4;  
    
    private volatile boolean stop = false;
	
	public SelectorHandler (Selector selector) {
		this.selector = selector;
	}
	
	public void stop () {
		this.stop = true;
	}
	
	/**
	 * 监听选择器通知
	 */
	public void listen () {
		while (!stop) {  
            try {  
            	log.info("listen ... ");  
                //选择一组键，其相应的通道已为 I/O 操作准备就绪。     
                int readyChannels = this.selector.select();
                log.info("readyChannels:{}", readyChannels);
                if(readyChannels == 0) {//防止selector.wakeup()唤醒线程，又没有实际通道时间
                	continue;
                }
                //返回此选择器的已选择键集     
                Iterator<SelectionKey> selectorKeys = this.selector.selectedKeys().iterator();  
                SelectionKey key = null;
                while (selectorKeys.hasNext()) {  
                    //这里找到当前的选择键     
                    key = selectorKeys.next(); 
                    //然后将它从返回键队列中删除     
                    selectorKeys.remove();
                    try {
						this.handleKey(key);
					} catch (Exception e) { //事件异常处理
						if (key != null) {
							key.cancel();
							if (key.channel() != null) {
								key.channel().close();
							}
						}
					}
                }  
            } catch (Exception e) {  
            	log.error(e.getMessage(), e);
            }  
        }  
		
		//多路复用器关闭后，所有注册在上面的channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
		if (this.selector != null) {
			try {
				this.selector.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	private void handleKey (SelectionKey key) throws IOException{
        log.info("key:{}", key.readyOps());
        if (!key.isValid()) { // 选择键无效  
        	log.info("key:{} is not valid", key.readyOps());
            return;  
        }
		if (key.isConnectable()) {  
            //链接服务端成功     
            this.connect(key);  
        } else if (key.isAcceptable()) {//accept是适用于 ServerSocketChannel 的唯一事件类型。
            //如果遇到客户端请求那么就响应 TODO 为什么既能接受SocketChannel,也能接受bio的Socket链接？
            this.accept(key);  
        } else if (key.isReadable()) {  
//        } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) { //和key.isReadable()相同
            //读取客户端的数据
        	/**
        	 * 一次isReadable事件能把receivebuffer读缓存区读满，例如receivebuffer初始化大小为4（一次最多读取4个字节），
        	 * 当客户端发送数据abcdef(6个字节)，会产生2次isReadable事件，第一次读4个字节（读到abcd），因为receivebuffer初始化最多
        	 * 读4个字节信息，第二次读2个字节（读到ef）。
        	 * 注意:当client端断开的时候，server端也会收到isReadable事件，但是读取到的字节数为-1。(只有socket是这样还是socketChannel也是？)
        	 */
        	
            this.read1(key);  
        } else if (key.isWritable()) {  
            //向客户端写数据     
            this.write(key);  
        } 
	}
	
	private void connect (SelectionKey key) throws IOException {
		log.info("client connecting......");  
		SocketChannel client = (SocketChannel) key.channel();  
        // 判断此通道上是否正在进行连接操作。  
        // 完成套接字通道的连接过程。  
        if (client.isConnectionPending()) {  
            client.finishConnect();  
            log.info("connected!");  
            this.writeText(client, "Hello,Server");
        }  
        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);  
	}
	
	/**   
     * 这个方法是连接   
     * 客户端连接服务器   
     * @throws IOException    
     * */  
	private void accept(SelectionKey key) throws IOException {  
		log.info("client accepting......");  
        ServerSocketChannel server = (ServerSocketChannel) key.channel();  
        // 接受到此通道套接字的连接。  
        // 此方法返回的套接字通道（如果有）将处于阻塞模式。 
        SocketChannel client = server.accept();  
        // 配置为非阻塞 
        client.configureBlocking(false);  
        //OP_READ用于读取操作的操作集位     
        client.register(this.selector, SelectionKey.OP_READ);
        //只能接受读事件，如果同时接受写事件的话会不停的循环接受到写事件，因为该通道一直处于可以写的状态
//        client.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);  
    }  
  
    /**   
     * 从通道中读取数据   
     * 并且判断是给那个服务通道的   
     * @throws IOException    
     * */  
	private void read(SelectionKey key) throws IOException {  
        //通过选择键来找到之前注册的通道     
        SocketChannel client = (SocketChannel) key.channel();  
        /*发送数据缓冲区*/  
        //将缓冲区清空以备下次读取 
        ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK); 
        receivebuffer.clear();  
        //从通道里面读取数据到缓冲区并返回读取字节数     
        int count = client.read(receivebuffer);  
        log.info("read count:{}", count);
        if (count == -1) {//当client端断开的时候，server端也会收到isReadable事件，但是读取到的字节数为-1。(只有socket是这样还是socketChannel也是？)
            //取消这个通道的注册     
            key.channel().close();  
            key.cancel();  
            return;  
        } else if (count > 0) {  
        	//将数据从缓冲区中拿出来     
        	String receiveText = new String(receivebuffer.array(), 0, count);  
        	log.info("receive message from client:{}", receiveText);  
        } 
    }  
	
	private void read1(SelectionKey key) throws IOException {
		// Read the data
		SocketChannel sc = (SocketChannel) key.channel();
		// Echo data
		int bytesEchoed = 0;
		StringBuilder stringBuilder = new StringBuilder();
		//将缓冲区清空以备下次读取 
        ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK); 
		while (true) {
			receivebuffer.clear();
			int count = sc.read(receivebuffer);
			if (count == -1) {//当client端断开的时候，server端也会收到isReadable事件，但是读取到的字节数为-1。(只有socket是这样还是socketChannel也是？)
	            //取消这个通道的注册     
	            sc.close();  
	            key.cancel();  
	            log.info("client:{} is closed", sc);
	            return;  
	        }
			if (count == 0) {//标示此次传输client已经没有需要传输的数据
				break;
			}
			bytesEchoed += count;
			stringBuilder.append(new String(receivebuffer.array(), 0, count));
			receivebuffer.flip();
			sc.write(receivebuffer);
		}
		log.info("Send bytes count:{} to client:{} message:{}", bytesEchoed, sc, stringBuilder.toString());
	}
    
	private void write(SelectionKey key) throws IOException {  
        // 返回为之创建此键的通道。  
        SocketChannel client = (SocketChannel) key.channel();  
        String sendText = "message from server--";
        this.writeText(client, sendText);
    }
	
	private void writeText(SocketChannel client, String sendText) throws IOException {
	    /*接受数据缓冲区*/  
	    ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);  
		//将缓冲区清空以备下次写入  
        sendbuffer.clear();  
        //向缓冲区中输入数据  
        sendbuffer.put(sendText.getBytes());  
        //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
        sendbuffer.flip();  
        //输出到通道  
        client.write(sendbuffer);  
        log.info("Server send message to client:{}", sendText);  
	}
}
