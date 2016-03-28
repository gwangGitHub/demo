package com.gwang.rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.gwang.rmi.iface.RMIInterface;

public class RMIServerDemo implements RMIInterface {
	private int port;
	private String serverName;
	
	public RMIServerDemo (int port, String serverName) {
		this.port = port;
		this.serverName = serverName;
	}
	
	public String echo(String msg) throws RemoteException {
		if ("quit".equalsIgnoreCase(msg)) {
			System.out.println("Server will be shutdown");
			System.exit(0);
		}
		System.out.println("message from client:" + msg);
		return "Server response:" + msg;
	}
	
	public void startServer() {
		try {
			UnicastRemoteObject.exportObject(this, port);
			Registry registry = LocateRegistry.createRegistry(port);
			registry.rebind(serverName, this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		RMIServerDemo server = new RMIServerDemo(8080, "RMIServerDemo");
		server.startServer();
	}
}
