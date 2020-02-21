package com.kingdombiao.blockingQueue;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 描述:
 * 阻塞队列
 *
 * @author biao
 * @create 2019-08-20 11:05
 */
@Slf4j
public class BlockingQueueDemo {


    @Test
    public void testArrayBlockingQueue() throws InterruptedException, IOException {

        PropertyConfigurator.configure("C:\\studyDemo\\demoStudy\\src\\main\\resources\\log4j.properties");

        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(2);

        /*blockingQueue.put(new Random().nextInt());
        blockingQueue.take();
        blockingQueue.peek();
        blockingQueue.element();
        blockingQueue.poll(1,TimeUnit.SECONDS);*/

        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();

       /* linkedBlockingQueue.put(new Random().nextInt());

        linkedBlockingQueue.put(new Random().nextInt());
        linkedBlockingQueue.put(new Random().nextInt());

        linkedBlockingQueue.take();
        linkedBlockingQueue.element();
        linkedBlockingQueue.poll();
        linkedBlockingQueue.offer(new Random().nextInt());
        linkedBlockingQueue.add(new Random().nextInt());*/

        /*PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();
        priorityBlockingQueue.put(5);
        priorityBlockingQueue.put(2);
        priorityBlockingQueue.put(4);
        priorityBlockingQueue.put(1);
        priorityBlockingQueue.put(3);
        priorityBlockingQueue.put(0);

        DelayQueue<Delayed> delayQueue = new DelayQueue<>();

        SynchronousQueue<Object> synchronousQueue = new SynchronousQueue<>();

        LinkedTransferQueue linkedTransferQueue = new LinkedTransferQueue();

        LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque();*/

       //log.info(""+negation(1230));






       /* new Thread(){
            @Override
            public void run() {
                try {
                    while (true){
                        int anInt = new Random().nextInt();
                        blockingQueue.put(anInt);
                        System.out.println("生产了："+anInt);
                        TimeUnit.SECONDS.sleep(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                try {
                    while (true){
                        Integer take = blockingQueue.take();
                        System.out.println("消费了："+take);
                        TimeUnit.SECONDS.sleep(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        System.in.read();*/


        /*while (true){
            //blockingQueue.add(new Random().nextInt());  //队列满了回抛 java.lang.IllegalStateException: Queue full

           *//* boolean offer = blockingQueue.offer(new Random().nextInt());
            //System.out.println("offer result:"+offer);
            log.info("offer result:"+offer);
            TimeUnit.SECONDS.sleep(1);*//*

            blockingQueue.put(new Random().nextInt());



            //blockingQueue.remove(); //队列为空会抛 java.util.NoSuchElementException
           *//* int nextInt = new Random().nextInt(100);
            System.out.println("生产了--->"+nextInt);
            blockingQueue.put(nextInt); //队列满了，等待
            Integer integer = blockingQueue.take();//队列为空，等待
            System.out.println("消费了--->"+integer);*//*
        }*/

    }

    private static String negation(int x) {
        int input = x;
        if (input < 0) {
            input = -input;
        }
        String result="";
        String s = String.valueOf(input);
        String[] split = s.split("");
        for(int i=split.length-1;i>=0;i--){
            if("0".equals(split[i])){
                continue;
            }
            result+=split[i];
        }
        if (x < 0) {
            return ("-"+result);
        }
        return result;

        /*int input = x;
        if (input < 0) {
            input = -input;
        }
        int result = 0;
        int temp = 0;
        while (input > 0) {
            temp = input % 10;
            // 溢出，可能存在的问题：负数和正数不一样
            // 乘可能造成的溢出
            if (Integer.MAX_VALUE / 10 < result) {
                result = 0;
                break;
            }
            // 加可能造成的溢出
            if (Integer.MAX_VALUE - result * 10 < temp) {
                result = 0;
                break;
            }
            result = result * 10 + temp;
            input = input / 10;
        }
        if (x < 0) {
            return -result;
        }
        return result;*/
    }


}
