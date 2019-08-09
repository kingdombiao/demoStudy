package com.kingdombiao.lockTest;

import com.kingdombiao.lock.MyAQSLock;
import com.kingdombiao.lock.MyLock;

/**
 * Hello world!
 *
 */
public class MyAQSLockTest {
    public static void main( String[] args ) throws InterruptedException {

        MyAQSLock lock = new MyAQSLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("第一个线程");
                lock.unlock();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("第二个线程");
                lock.unlock();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("第三个线程");
                lock.unlock();
            }
        }).start();


        //System.out.println(lock.getValue());


    }
}
