package com.kingdombiao.producerConsumer.condition;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述:
 * 容器
 *
 * @author biao
 * @create 2019-08-14 11:07
 */
public class Container {
    private final Lock lock = new ReentrantLock();
    //生产者线程
    private final Condition notFull = lock.newCondition();
    //消费者线程
    private final Condition notEmpty = lock.newCondition();
    //容器的最大容量
    private Integer capacity;
    private List<Integer> list=new LinkedList<>();


    public Container(Integer capacity) {
        this.capacity = capacity;
    }

    public void  put(Integer value){

        lock.lock();
        try {
            while(list.size()== capacity){
                System.out.println("the list is full........");
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(value);
            System.out.println("producer-->"+ Thread.currentThread().getName()+"--put:" + value+"----size:"+ list.size());
            notEmpty.signal();
        }finally {
            lock.unlock();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Integer take(){
        lock.lock();
        try {
            while (list.size()==0){
                System.out.println("the list is empty.........");
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Integer value = list.remove(0);
            System.out.println("consumer-->"+ Thread.currentThread().getName()+"-->take:" + value+"-->size:"+list.size());
            notFull.signal();
            return value;
        } finally {
            lock.unlock();
        }
    }
}
