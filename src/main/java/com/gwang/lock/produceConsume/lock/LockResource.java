package com.gwang.lock.produceConsume.lock;

import com.gwang.lock.produceConsume.AbstractResource;
import com.gwang.lock.produceConsume.Consumer;
import com.gwang.lock.produceConsume.Producer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wanggang on 2018/10/16.
 * ReentrantLock的condition可以把生产者和消费者分为两组,当队列满时等待生产组（await produceBlockCondition）,
 * 唤醒消费组(signalAll consumeBlockCondition),当队列空时,等待消费组(await consumeBlockCondition),
 * 唤醒生产组(signalAll produceBlockCondition)。做到有针对的等待和唤醒,节省线程切换带来的消耗,避免Sync带来的问题。
 */
public class LockResource extends AbstractResource{

    private ReentrantLock lock = new ReentrantLock();
    private Condition produceBlockCondition = lock.newCondition();
    private Condition consumeBlockCondition = lock.newCondition();

    @Override
    public void add() {
        lock.lock();
        try {
            if (num < size) {
                num++;
                System.out.println(Thread.currentThread() + "生产num:" + num);
                consumeBlockCondition.signalAll();
            } else {
                System.out.println(Thread.currentThread() + "阻塞生产num:" + num);
                produceBlockCondition.await();
                System.out.println(Thread.currentThread() + "生产者被唤醒");
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public synchronized void remove() {
        lock.lock();
        try {
            if (num > 0) {
                System.out.println(Thread.currentThread() + "消费num:" + num);
                num--;
                produceBlockCondition.signalAll();
            } else {
                System.out.println(Thread.currentThread() + "阻塞消费num:" + num);
                consumeBlockCondition.await();
                System.out.println(Thread.currentThread() + "消费者被唤醒");
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockResource resource = new LockResource();
        Producer producer = new Producer(resource);
        Consumer consumer = new Consumer(resource);
        producer.run();
        consumer.run();
    }
}
