package com.kingdombiao.producerConsumer.semaphore;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author biao
 * @create 2019-08-13 15:46
 */
public class ProducerConsumerTest {

    public static void main(String[] args) {
        Container container = new Container();

        for(int i=0;i<20;i++)
            new Thread(new Producer(container)).start();

        for(int i=0;i<20;i++)
            new Thread(new Consumer(container)).start();

    }
}
