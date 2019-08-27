package com.kingdombiao.distributedlocks;

import java.util.concurrent.CountDownLatch;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-27 9:55
 */
public class ZkLockDemo {
    static Integer num = 10;

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 1; i <= num; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ZkDistributedLock distributedLock = new ZkDistributedLock();
                distributedLock.lock();
                System.out.println(Thread.currentThread().getName() + "卖出第" + num-- + "张票!");
                distributedLock.unlock();
            },"售票员<"+i+">").start();
            countDownLatch.countDown();
        }
    }
}
