package com.kingdombiao.ConcurrentContainer;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 描述:
 * 并发容器 ConcurrentLinkedQueue
 *
 * @author biao
 * @create 2019-08-19 15:42
 */
public class ConcurrentLinkedQueueDemo {


    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        concurrentLinkedQueue.add("a");
        concurrentLinkedQueue.add("b");
        concurrentLinkedQueue.add("c");
        //System.out.println(concurrentLinkedQueue.poll());

    }

}
