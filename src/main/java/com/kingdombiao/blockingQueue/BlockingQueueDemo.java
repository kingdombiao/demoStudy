package com.kingdombiao.blockingQueue;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 描述:
 * 阻塞队列
 *
 * @author biao
 * @create 2019-08-20 11:05
 */
public class BlockingQueueDemo {

    @Test
    public void testArrayBlockingQueue() throws InterruptedException {

        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(10);

        while (true){
            //blockingQueue.add(new Random().nextInt());  //队列满了回抛 java.lang.IllegalStateException: Queue full
            //blockingQueue.remove(); //队列为空会抛 java.util.NoSuchElementException
            int nextInt = new Random().nextInt(100);
            System.out.println("生产了--->"+nextInt);
            blockingQueue.put(nextInt); //队列满了，等待
            Integer integer = blockingQueue.take();//队列为空，等待
            System.out.println("消费了--->"+integer);
        }

    }


}
