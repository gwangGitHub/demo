package com.gwang.lock.produceConsume.sync;

import com.gwang.lock.produceConsume.AbstractResource;
import com.gwang.lock.produceConsume.Consumer;
import com.gwang.lock.produceConsume.Producer;

/**
 * Created by wanggang on 2018/10/16.
 * Sync的wait和notifyAll方法无法分为消费组和生产组,所以在资源队列长度很小的情况下（假设为1）,
 * 可能会出现生产者和消费者线程都调用wait方法等待,当调用notifyAll方法的时候等待的生产者和消费者一起被唤醒,
 * 但是当队列满时只是想唤起消费线程消费,这样就可能唤起的是生产者线程,生产者线程发现队列是满的再wait,再调用
 * notifyAll方法,直到被唤醒的是消费者线程才能正常运行下去,这样消耗了很多没必要的线程切换和等待。
 */
public class SyncResource extends AbstractResource {

    @Override
    public synchronized void add() {
        if (num < size) {
            num ++;
            System.out.println(Thread.currentThread() + "生产num:" + num);
            this.notifyAll();
        } else {
            try {
                System.out.println(Thread.currentThread() + "阻塞生产num:" + num);
                this.wait();
                System.out.println(Thread.currentThread() + "生产者被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void remove() {
        if (num > 0) {
            System.out.println(Thread.currentThread() + "消费num:" + num);
            num--;
            this.notifyAll();
        } else {
            try {
                System.out.println(Thread.currentThread() + "阻塞消费num:" + num);
                this.wait();
                System.out.println(Thread.currentThread() + "消费者被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SyncResource resource = new SyncResource();
        Producer producer = new Producer(resource);
        Consumer consumer = new Consumer(resource);
        producer.run();
        consumer.run();
    }
}
