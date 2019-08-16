package com.kingdombiao.thead.cyclicBarrier;

import java.io.Writer;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-16 13:53
 */
public class cyclicBarrierDemo {

    public static void main(String[] args) throws InterruptedException {
        int n=4;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(n);

        for (int i=0;i<n;i++){
            new Writer(cyclicBarrier).start();
        }

    }

    static class Writer extends Thread{

        private CyclicBarrier cyclicBarrier;

        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }
}
