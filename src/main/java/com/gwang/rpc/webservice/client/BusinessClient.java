package com.gwang.rpc.webservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wanggang on 25/03/2017.
 */
public class BusinessClient {

    private static final Logger log = LoggerFactory.getLogger(BusinessClient.class);

    public static void main(String[] args) {
        BusinessService businessService = new BusinessService();
        Business business = businessService.getBusinessPort();
        SerializeDemo result = business.echo("Hello");
        log.info("result,id:{},value:{}", result.getId(), result.getValue());
    }
}
