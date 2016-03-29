package com.gwang.socket.bio.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketDemo {
	
	public static void main(String[] args) throws Exception{
		Socket sock = new Socket("localhost", 9211);
        OutputStream out = sock.getOutputStream();  
        InputStream sin = sock.getInputStream();  
        byte buf [] = new byte[512];  
        buf = "OutputStream...".getBytes();  
        out.write(buf);
        out.flush();
        System.out.println("send ok ---> " + sin + "     Out: " + out);  
        byte ibuf[] = new byte[512];  
        int len = sin.read(ibuf);  
        String s = new String(ibuf, 0, len-1);  
        System.out.print(len + s);  
	}
}
