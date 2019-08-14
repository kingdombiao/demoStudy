package com.kingdombiao.producerConsumer.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * 消费者
 *
 * @author biao
 * @create 2019-08-14 10:35
 */
public class Consumer implements Runnable{

    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
                if(queue.size()==0){
                    System.out.println("the queue is empty,the consumer thread is waiting................");
                }
                Integer item = queue.take();
                System.out.println("consumer:" + Thread.currentThread().getName() + " consume:" + item+";the size of the queue:" + queue.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
