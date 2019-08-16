package com.kingdombiao.thead.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-16 16:12
 */
public class SemaphoreDemo {
    private static final int COUNT=40;

    private static ExecutorService executorService=Executors.newCachedThreadPool();

    private static Semaphore semaphore=new Semaphore(10);

    public static void main(String[] args) {
        for (int i=0;i<COUNT;i++){
            executorService.execute(new Task());
        }

        executorService.shutdown();

    }

    static class Task implements Runnable{

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() +"正在存储数据.........");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
