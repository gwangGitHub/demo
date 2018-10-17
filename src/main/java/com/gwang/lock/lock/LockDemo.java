package com.gwang.lock.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by wanggang on 2018/8/24.
 */
public class LockDemo {
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    private Lock lock = new ReentrantLock();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        final LockDemo demo = new LockDemo();

        new Thread("A") {
            public void run() {
                demo.insert(Thread.currentThread());
            };
        }.start();

        new Thread("B") {
            public void run() {
                demo.insert(Thread.currentThread());
            };
        }.start();
    }

    public void insert(Thread thread) {
        lock.lock();
        readWriteLock.writeLock().lock();
        if (lock.tryLock()) {
            try {
                System.out.println("线程" + thread.getName() + "得到了锁...");
                for (int i = 0; i < 9999; i++) {
                    arrayList.add(i);
                }
            } catch (Exception e) {

            } finally {
                System.out.println("线程" + thread.getName() + "释放了锁...");
                lock.unlock();
            }
        } else {
            System.out.println("线程" + thread.getName() + "获取锁失败...");
        }
    }
}
