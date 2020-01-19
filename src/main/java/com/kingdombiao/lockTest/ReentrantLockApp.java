package com.kingdombiao.lockTest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-05 17:30
 */
public class ReentrantLockApp {

    public static void main(String[] args) {

        ReentrantLock reentrantLock = new ReentrantLock(true);
  /*
        new Thread(){
            @Override
            public void run() {
                System.out.println("线程一释放锁");
                reentrantLock.unlock();
                System.out.println("线程一执行");
                reentrantLock.lock();

            }
        }.start();

     new Thread(){
            @Override
            public void run() {
                System.out.println("线程二执行");
                reentrantLock.lock();
                System.out.println("线程二释放锁");
                reentrantLock.unlock();
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                System.out.println("线程三执行");
                reentrantLock.lock();
                System.out.println("线程三释放锁");
                reentrantLock.unlock();

            }
        }.start();*/

        Condition condition1 = reentrantLock.newCondition();
        new Thread(){
            @Override
            public void run() {
                try {
                    reentrantLock.lock();
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    reentrantLock.unlock();
                }
            }
        }.start();

       /* new Thread(){
            @Override
            public void run() {
                try {
                    reentrantLock.lock();
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    reentrantLock.unlock();
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                try {
                    reentrantLock.lock();
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    reentrantLock.unlock();
                }
            }
        }.start();*/

        new Thread(){
            @Override
            public void run() {
                try {
                    reentrantLock.lock();
                    condition1.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    reentrantLock.unlock();
                }
            }
        }.start();


    }
}
