package com.gwang.rpc.springrmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wanggang on 25/03/2017.
 */
public class ClientDemo {

    private static final Logger log = LoggerFactory.getLogger(ClientDemo.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springrmi/client.xml");
        Business service = (Business) context.getBean("businessService");
        String result = service.echo("wgwg");
        log.info("result:{}", result);
    }
}
