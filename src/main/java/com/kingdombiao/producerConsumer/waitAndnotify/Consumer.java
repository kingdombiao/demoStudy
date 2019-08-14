package com.kingdombiao.producerConsumer.waitAndnotify;

/**
 * 描述:
 * 消费者
 *
 * @author biao
 * @create 2019-08-13 15:44
 */
public class Consumer implements Runnable {

    private Container container;

    public Consumer(Container container) {
        this.container = container;
    }

    @Override
    public void run() {
        container.take();
    }
}
