package com.gwang.lock.semaphore;

import java.util.concurrent.*;

/**
 * Created by wanggang on 2018/2/9.
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(5);
//        ExecutorService executorService= Executors.newFixedThreadPool(3) ;
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10, 100, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(50));

        for( int i=0; i<50; i++){
            final String temp = ""+i;

            Runnable run =new Runnable() {

                @Override
                public void run() {
                    try {
                        semaphore.acquire();

                        System.out.println(temp + ":" + Thread.currentThread().getName() + " poolsize:" + executor.getQueue().size());
//                        Thread.sleep((long)Math.random()*2000);
                        Thread.sleep(2000);
                        semaphore.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            };
            executor.execute(run);
        }
    }
}
