package com.gwang.lock.produceConsume.blockingQueue;

import com.gwang.lock.produceConsume.AbstractResource;
import com.gwang.lock.produceConsume.Consumer;
import com.gwang.lock.produceConsume.Producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wanggang on 2018/10/17.
 */
public class QueueResource extends AbstractResource {

    AtomicInteger num = new AtomicInteger(1);

    private BlockingQueue queueResource = new LinkedBlockingDeque(10);

    @Override
    public void add() {
        try {
            queueResource.put(num);
            System.out.println(Thread.currentThread() + "生产num:" + num);
            num.incrementAndGet();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void remove() {
        try {
            int take = num.decrementAndGet();
            queueResource.take();
            System.out.println(Thread.currentThread() + "消费num:" + take);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        QueueResource resource = new QueueResource();
        Producer producer = new Producer(resource);
        Consumer consumer = new Consumer(resource);
        producer.run();
        consumer.run();
    }
}
