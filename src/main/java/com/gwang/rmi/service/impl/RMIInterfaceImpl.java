package com.gwang.rmi.service.impl;

import java.rmi.RemoteException;

import com.gwang.rmi.service.iface.RMIInterface;

public class RMIInterfaceImpl implements RMIInterface {

	public String echo(String msg) throws RemoteException {
		if ("quit".equalsIgnoreCase(msg)) {
			System.out.println("Server will be shutdown");
			System.exit(0);
		}
		System.out.println("message from client:" + msg);
		return "Server response:" + msg;
	}

}
