package com.kingdombiao.lock;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 模拟公平锁
 *
 * @author kingdombiao
 * @create 2019-08-06 10:07
 */
public class MyFairLock {

    private boolean isLocked=false;
    private Thread lockThred=null;
    private List<QueueObject> waitThreadList =new ArrayList<>(); //监视器队列


    public void lock() throws InterruptedException {
        //调用一次lock就会创建一个对象来标记当前线程
        QueueObject queueObject = new QueueObject();
        boolean isLockForCurrentThead=true;
        synchronized (this){
            //不管有没有获得公平锁，先放入队列中
            waitThreadList.add(queueObject);
        }

        while (isLockForCurrentThead){
            synchronized (this){
                /**
                 * 如果isLockForCurrentThead=false的时候，则当前线程获取FairLock锁,必须满足两个条件
                 *(1)、当前没有其它线程占用FairLock对象，也就是说isLocked=false;
                 *(2)、当前线程在监视器队列waitThreadList的头部
                 *如果当前线程不在头部，主动 doWait()
                 */
                isLockForCurrentThead=isLocked || waitThreadList.get(0)!=queueObject;
            }

            if(!isLockForCurrentThead){
                isLocked=true; //标记锁被占用
                waitThreadList.remove(queueObject); //移除该线程
                lockThred=Thread.currentThread();
                return;
            }
        }
        try {
            //果然FairLock锁已被占用，或者当前线程不在监视器队列的头部，直接等待
            queueObject.doWait();
        } catch (InterruptedException e) {
           synchronized (this){
               waitThreadList.remove(queueObject);
           }
           throw e;
        }
    }

    public synchronized void unLock(){
        if(this.lockThred !=Thread.currentThread()){
            throw new IllegalMonitorStateException("Calling thread has not locked this lock");
        }
        isLocked=false;
        lockThred=null;
        if(waitThreadList.size()>0){
            waitThreadList.get(0).doNotify();
        }

    }
}

/**
 * 监视器对象
 */
class QueueObject {

    private boolean isNotified = false;

    public synchronized void doWait() throws InterruptedException {
        /**
         * 使用while形式封装监视器的wait()和notify()好处多多
         * 1、防止wait错过信号
         * 2、防止假唤醒
         * 3、多个线程等待相同的信号
         */
        while (!isNotified) {
            this.wait();
        }
        isNotified = false;
    }

    public synchronized void doNotify() {
        this.isNotified = true;
        this.notify();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}
