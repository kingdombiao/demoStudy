package com.kingdombiao.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-05 16:22
 */
public class MyLock  implements Lock {

    private boolean isLocked = false;
    private  volatile  int value;
    private Thread thread;

    @Override
    public synchronized void lock() {
        Thread currentThred = Thread.currentThread();
        while (isLocked && currentThred!=thread){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isLocked=true;
            value++;
            thread=currentThred;
        }
    }


    @Override
    public synchronized void unlock() {
        if(thread==Thread.currentThread()){
            value--;
            if(value==0){
                notifyAll();
                isLocked=false;
                thread=null;
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }



    @Override
    public Condition newCondition() {
        return null;
    }

    public int getValue() {
        return value;
    }
}
