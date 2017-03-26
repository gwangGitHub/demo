package com.gwang.rpc.webservice;

import com.gwang.serialize.SerializeDemo;

public interface Business {

	public SerializeDemo echo(String msg);
	
}
