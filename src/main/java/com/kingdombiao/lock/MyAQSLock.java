package com.kingdombiao.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-05 18:06
 */
public class MyAQSLock implements Lock {

   private final Sync sync;


    public MyAQSLock() {
        this.sync = new Sync();
    }

   protected class Sync extends AbstractQueuedSynchronizer{
        @Override
        protected boolean tryAcquire(int arg) {
            int state=getState();
            Thread currentThread = Thread.currentThread();
            if(state==0){
                if(compareAndSetState(0,arg)){
                    setExclusiveOwnerThread(currentThread);
                    return true;
                }
            }else if(getExclusiveOwnerThread()==currentThread){
                setState(state+1);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if(Thread.currentThread()!=getExclusiveOwnerThread()){
                throw new RuntimeException();
            }
            int state=getState()-arg;
            boolean flag=false;
            if(state==0){
                setExclusiveOwnerThread(null);
                flag=true;
            }
            setState(state);
            return flag;
        }

       final ConditionObject newCondition() {
           return new ConditionObject();
       }
    }




    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
