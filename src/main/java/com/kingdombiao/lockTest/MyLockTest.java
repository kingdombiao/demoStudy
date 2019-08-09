package com.kingdombiao.lockTest;

import com.kingdombiao.lock.MyLock;
/**
 * Hello world!
 *
 */
public class MyLockTest {
    public static void main( String[] args ) throws InterruptedException {

        MyLock lock = new MyLock();

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


        while (Thread.activeCount() !=1){
            //自旋
            System.out.println("一直自旋嘛.....");
        }

        System.out.println(lock.getValue());


    }
}
