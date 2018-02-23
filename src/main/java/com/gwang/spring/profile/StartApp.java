package com.gwang.spring.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Created by wanggang on 2018/1/2.
 * 样例，展示如何使用spring的profile功能，区分不同的环境来读取不同的配置文件
 */
public class StartApp {

    private static final Logger log = LoggerFactory.getLogger(StartApp.class);

    /**
     * 有几种方法来区分环境
     * 1.ENV方式： 通过代码设置，ctx.getEnvironment().setActiveProfiles(env); ctx.refresh();
     * 2.JVM参数方式: 右键点击打开“Run Configurations”，在“VM arguments”中输入如下内容：-Dspring.profiles.active="production"
     * 3.web.xml方式：
     * <init-param>
     *      <param-name>spring.profiles.active</param-name>
     *      <param-value>test</param-value>
     * </init-param>
     * 4.标注方式（junit单元测试非常实用）：
     * @ActiveProfiles({"test","productprofile"})
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring.profile/applicationContext.xml");

        //配合方法一，从设置的变量中获取环境
        String env = System.getProperty("environment");
        log.info("env:" + env);
        env = "local";
        //方法一，通过代码设置
        ctx.getEnvironment().setActiveProfiles(env);
        ctx.refresh();

        Map<Long, Long> businessMap = (Map<Long, Long>) ctx.getBean("businessMap");
        for (Map.Entry<Long, Long> entry : businessMap.entrySet()) {
            log.info("key:{},value:{}",entry.getKey(),entry.getValue());
        }
    }
}
