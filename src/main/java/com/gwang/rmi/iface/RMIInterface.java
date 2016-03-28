package com.gwang.rmi.iface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote{
	
	public String echo(String msg) throws RemoteException;
}
