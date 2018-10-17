package com.gwang.lock.produceConsume;

import java.util.Random;

/**
 * Created by wanggang on 2018/10/16.
 */
public class Producer {

    private AbstractResource resource;
    private int producerNum = 5;

    public Producer(AbstractResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 0; i < producerNum; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Random random = new Random();
                            long time = random.nextInt(3) * 1000l;
                            Thread.sleep(time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        resource.add();
                    }
                }
            }).start();
        }
    }
}
