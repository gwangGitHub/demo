package com.gwang.rpc.webservice;

import com.gwang.serialize.SerializeDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

//targetNamespace定义了生成的client类所在位置
@WebService(name = "Business", serviceName = "BusinessService", targetNamespace = "http://webservice.rpc.gwang.com/client")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class BusinessImpl implements Business {
	
	private static final Logger log = LoggerFactory.getLogger(BusinessImpl.class);

	private int id = 0;

	//webserivce接口返回的类不需要继承Serializable接口，在调用wsdl生成客户端代码的时候会一并生成该类
	@Override
	public SerializeDemo echo(String msg) {
		log.info("message from client:{}", msg);
		if ("quit".equalsIgnoreCase(msg)) {
			System.out.println("Server will be shutdown");
			System.exit(0);
		}
		log.info("message from client:{}", msg);
		SerializeDemo demo = new SerializeDemo(++id, msg);
		return demo;
	}

	public static void main(String[] args) {
		Endpoint.publish("http://127.0.0.1:9527/BusinessService", new BusinessImpl());
		//在控制台运行 wsimport -keep http://127.0.0.1:9527/BusinessService?wsdl 生成客户端代码
		log.info("Server has been started");
	}

}
