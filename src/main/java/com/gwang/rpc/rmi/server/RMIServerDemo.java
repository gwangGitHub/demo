package com.gwang.rpc.rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.gwang.rpc.rmi.service.iface.RMIInterface;
import com.gwang.rpc.rmi.service.impl.RMIInterfaceImpl;

public class RMIServerDemo {
	private int port;
	private String serverName;
	
	public RMIServerDemo (int port, String serverName) {
		this.port = port;
		this.serverName = serverName;
	}
	
	public void startServer() {
		try {
			RMIInterface serviceDemo = new RMIInterfaceImpl();
			UnicastRemoteObject.exportObject(serviceDemo, 9527);
			Registry registry = LocateRegistry.createRegistry(port);
			registry.rebind(serverName, serviceDemo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		RMIServerDemo server = new RMIServerDemo(1099, "RMIServerDemo");
		server.startServer();
	}
}
