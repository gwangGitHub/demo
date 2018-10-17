package com.gwang.lock.produceConsume;

/**
 * Created by wanggang on 2018/10/17.
 */
public abstract class AbstractResource {

    protected int num = 0;
    protected int size = 1;

    //生产者添加资源
    public abstract void add();
    //消费者消费资源
    public abstract void remove();
}
