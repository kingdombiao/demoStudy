package com.kingdombiao.producerConsumer.condition;

/**
 * 描述:
 * 消费者
 *
 * @author biao
 * @create 2019-08-14 13:32
 */
public class Consumer implements Runnable {

    Container container;

    public Consumer(Container container) {
        this.container = container;
    }

    @Override
    public void run() {
        while (true)
            container.take();
    }
}
