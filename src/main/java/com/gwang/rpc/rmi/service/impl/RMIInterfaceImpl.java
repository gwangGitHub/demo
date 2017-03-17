package com.gwang.rpc.rmi.service.impl;

import com.gwang.rpc.rmi.service.iface.RMIInterface;
import com.gwang.serialize.SerializeDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

public class RMIInterfaceImpl implements RMIInterface {
	private static final Logger log = LoggerFactory.getLogger(RMIInterfaceImpl.class);

	private int id = 0;

	public SerializeDemo echo(String msg) throws RemoteException {
		if ("quit".equalsIgnoreCase(msg)) {
			System.out.println("Server will be shutdown");
			System.exit(0);
		}
		log.info("message from client:{}", msg);
		SerializeDemo demo = new SerializeDemo(++id, msg);
		return demo;
	}

}
