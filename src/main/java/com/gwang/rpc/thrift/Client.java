package com.gwang.rpc.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client {
	public static void main(String[] args) throws Exception{  
        TSocket socket = new TSocket("127.0.0.1", 9090);  
        socket.setTimeout(3000);  
        TTransport transport = new TFramedTransport(socket);  
//        TProtocol protocol = new TCompactProtocol(transport);  
        TProtocol protocol = new TBinaryProtocol(transport);	//二进制传输协议
        transport.open();  
        System.out.println("Connected to Thrfit Server");  
          
        DemoService.Client client = new DemoService.Client.Factory()  
                .getClient(protocol);  
        String result = client.sayHello("WG11");  
        System.out.println(result);  
    } 
}
