package com.kingdombiao.producerConsumer.blockingQueue;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 描述:
 * BlockingQueue
 *
 * @author biao
 * @create 2019-08-13 15:46
 */
public class ProducerConsumerTest {

    public static void main(String[] args) {

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

            new Thread(new Producer(queue)).start();

            new Thread(new Consumer(queue)).start();

    }
}
