package com.kingdombiao.lockTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-06 13:44
 */
public class ReentrantReadWriteLockApp {

    private Map<String,Object> map=new HashMap<>();

    private ReadWriteLock rwl = new ReentrantReadWriteLock();

    Lock readLock = rwl.readLock();
    Lock writeLock= rwl.writeLock();


    public Object get(String key){
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + " 读操作在执行..");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
        return map.get(key);
    }

    public void put(String key,Object value){
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + " 写操作在执行..");
            Thread.sleep(1000);
            map.put(key,value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }


    public static void main(String[] args) {
        ReentrantReadWriteLockApp app = new ReentrantReadWriteLockApp();
        //app.put("key2", "value2");

        System.out.println(app.get("kkk"));
        System.out.println(app.get("kkk"));

        /*LockSupport.park(new Object());

        System.out.println("线程阻塞了... ");*/

       /* new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(app.get("key2"));
            }
        }).start();*/

       app.put("key2", "value2");
        //app.put("key3", "value2");

       new Thread(new Runnable() {
            @Override
            public void run() {
               app.put("key2", "value2");
            }
        }).start();
 /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(app.get("key2"));
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(app.get("key2"));
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(app.get("key2"));
            }
        }).start();*/

    }

}
