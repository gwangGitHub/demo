package com.gwang.pricing.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalEnvUtil {
    private static final Logger logger = LoggerFactory.getLogger(LocalEnvUtil.class);

//    private static final String HOST_NAME = ProcessInfoUtil.getHostNameInfoByIp();
//
//    private static final HostEnv EVN = ProcessInfoUtil.getHostEnv();
//
//    public static String getHostName(){
//        return HOST_NAME;
//    }
//
//    public static HostEnv getEnvironment(){
//        return EVN;
//    }
//
//    public static boolean isStage() {
//        return HostEnv.STAGING == EVN;
//    }

    public static boolean isProduction() {
//        return HostEnv.PROD == EVN;
        return false;
    }

//    public static boolean isTest() {
//        return HostEnv.STAGING != EVN && HostEnv.PROD != EVN;
//    }
//
//    public static String getTraceId() {
//        try {
//            return Tracer.id();
//        } catch (Exception e) {
//            logger.error("getTraceId error", e);
//        }
//
//        return "unknownTrace";
//    }
}
