package com.kingdombiao.producerConsumer.condition;

import java.util.Random;

/**
 * 描述:
 * 生产者
 *
 * @author biao
 * @create 2019-08-14 13:29
 */
public class Producer implements Runnable {

    Container container;

    public Producer(Container container) {
        this.container = container;
    }

    @Override
    public void run() {
        while (true)
            container.put(new Random().nextInt(100));
    }
}
