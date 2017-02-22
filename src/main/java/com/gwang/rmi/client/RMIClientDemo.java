package com.gwang.rmi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.gwang.rmi.service.iface.RMIInterface;

public class RMIClientDemo {

	public static void main(String[] args) throws Exception {
		Registry registry = LocateRegistry.getRegistry("localhost");
		String name = "RMIServerDemo";
		RMIInterface rmi = (RMIInterface)registry.lookup(name);
		rmi.echo("haha");
	}
}
