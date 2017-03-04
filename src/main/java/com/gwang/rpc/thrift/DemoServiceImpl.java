package com.gwang.rpc.thrift;

import org.apache.thrift.TException;

public class DemoServiceImpl implements DemoService.Iface{

	@Override
	public String sayHello(String name) throws TException {
		return "Hello " + name;
	}
}
