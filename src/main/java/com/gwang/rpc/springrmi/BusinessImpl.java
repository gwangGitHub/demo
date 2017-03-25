package com.gwang.rpc.springrmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessImpl implements Business {
	
	private static final Logger log = LoggerFactory.getLogger(BusinessImpl.class);

	@Override
	public String echo(String msg) {
		log.info("message from client:{}", msg);
		if ("quit".equalsIgnoreCase(msg)) {
			System.out.println("Server will be shutdown");
			System.exit(0);
		}
		return "Server response:" + msg;
	}

}
