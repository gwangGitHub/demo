package com.gwang.rpc.springrmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wanggang on 25/03/2017.
 */
public class ServerDemo {

    private static final Logger log = LoggerFactory.getLogger(ServerDemo.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springrmi/server.xml");
        log.info("Server has been started");
    }
}
