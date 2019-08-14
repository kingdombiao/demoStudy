package com.kingdombiao.producerConsumer.blockingQueue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author biao
 * @create 2019-08-14 10:27
 */
public class Producer implements Runnable {

    private BlockingQueue<Integer> queue;

    private Integer capacity=10;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Random random=new Random();
        while (true){
            try {
                TimeUnit.MILLISECONDS.sleep(10);

                if(queue.size()==capacity){
                    System.out.println("the queue is full,the producer thread is waiting..................");
                }

                int item=random.nextInt(100);
                queue.put(item);
                System.out.println("producer:" + Thread.currentThread().getName() + " produce:" + item+";the size of the queue:" + queue.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
