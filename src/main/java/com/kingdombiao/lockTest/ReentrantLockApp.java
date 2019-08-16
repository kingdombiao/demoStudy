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

        ReentrantLock reentrantLock = new ReentrantLock();
        /*reentrantLock.lock();
        reentrantLock.unlock();*/

        Condition condition1 = reentrantLock.newCondition();
        Condition condition2 = reentrantLock.newCondition();

        try {
            reentrantLock.lock();
            condition1.await();
            //condition2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            condition1.signal();
        }

    }
}
