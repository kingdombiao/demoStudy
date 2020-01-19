package com.kingdombiao.thead;

import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-14 15:43
 */
public class JoinTest {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("i want to executor before  1.............");
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("i want to executor before  2.............");
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("估计你也不会执行.........");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("守护线程finally 不会执行");
                }
            }

        });

        thread1.setDaemon(true);
        thread1.start();
        thread.start();
        /*thread1.join();
        thread.join();*/
        System.out.println("i want to executor after.............");



    }

}
