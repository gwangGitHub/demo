package com.gwang.pricing.common;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;

public class LocalLoggerUtil {
    public static void logForTestWithJSON(final Logger logger, String format, Object... arguments) {
        //生产环境不打日志
        if(LocalEnvUtil.isProduction()) {
            return;
        }
        Object[] jsonArray = new Object[arguments.length];
        for (int index = 0; index < arguments.length; index++) {
            Object object = arguments[index];
            if (object instanceof Number || object instanceof String) {
                jsonArray[index] = object;
            } else {
                jsonArray[index] = JSONObject.toJSONString(object);
            }
        }
        //备机、线下打日志
        logger.info(format, jsonArray);
    }

    public static void logForTest(final Logger logger, String format, Object... arguments) {
        //生产环境不打日志
        if(LocalEnvUtil.isProduction()) {
            return;
        }

        //备机、线下打日志
        logger.info(format, arguments);
    }
}
