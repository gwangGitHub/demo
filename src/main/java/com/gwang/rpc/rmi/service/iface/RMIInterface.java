package com.gwang.rpc.rmi.service.iface;

import com.gwang.serialize.SerializeDemo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote{
	
	public SerializeDemo echo(String msg) throws RemoteException;
}
