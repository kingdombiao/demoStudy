package com.kingdombiao.producerConsumer.semaphore;

import com.kingdombiao.producerConsumer.semaphore.Container;

import java.util.Random;

/**
 * 描述:
 * 生产者
 *
 * @author unovo
 * @create 2019-08-13 15:42
 */
public class Producer implements Runnable {

    private Container container;

    public Producer(Container container) {
        this.container = container;
    }

    @Override
    public void run() {
       container.put(new Random().nextInt(100));
    }
}
