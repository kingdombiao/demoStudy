package com.kingdombiao.thead.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-16 9:59
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Worker worker1 = new Worker(countDownLatch, "张三");
        Worker worker2 = new Worker(countDownLatch, "李四");
        Worker worker3 = new Worker(countDownLatch, "王五");

        Boss boss = new Boss(countDownLatch);
        executor.execute(worker1);
        executor.execute(worker2);
        executor.execute(worker3);

        executor.execute(boss);

        executor.shutdown();

    }
}
