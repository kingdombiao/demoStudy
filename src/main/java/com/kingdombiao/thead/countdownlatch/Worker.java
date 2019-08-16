package com.kingdombiao.thead.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-16 9:52
 */
public class Worker implements Runnable{

    private CountDownLatch countDownLatch;
    
    private String name;

    public Worker(CountDownLatch countDownLatch, String name) {
        this.countDownLatch = countDownLatch;
        this.name = name;
    }

    @Override
    public void run() {
        this.doWork();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.name + "活干完了！");
        this.countDownLatch.countDown();
    }

    private void doWork() {
        System.out.println(this.name+" 正在干活....");
    }
}
