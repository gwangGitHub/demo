package com.gwang.rpc.rmi.client;

import com.gwang.rpc.rmi.service.iface.RMIInterface;
import com.gwang.serialize.SerializeDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClientDemo {
	private static final Logger log = LoggerFactory.getLogger(RMIClientDemo.class);

	public static void main(String[] args) throws Exception {
		Registry registry = LocateRegistry.getRegistry("localhost");
		String name = "RMIServerDemo";
		RMIInterface rmi = (RMIInterface)registry.lookup(name);
		SerializeDemo demo = rmi.echo("haha");
		log.info("Service result:{}", demo);
	}
}
