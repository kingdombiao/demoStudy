package com.kingdombiao.lockTest;

import com.kingdombiao.lock.MyFairLock;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-06 11:11
 */
@Slf4j
public class FairLockTest {

    public static void main(String[] args) {
        MyFairLock fairLock = new MyFairLock();

        new Thread(()->{
            try {
                Thread.sleep(3000);
                fairLock.lock();
                System.out.println(Thread.currentThread().getName()+" test1");
                fairLock.unLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(()->{
            try {
                Thread.sleep(3000);
                fairLock.lock();
                System.out.println(Thread.currentThread().getName()+" test2");
                fairLock.unLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(()->{
            try {
                Thread.sleep(3000);
                fairLock.lock();
                System.out.println(Thread.currentThread().getName()+" test3");
                fairLock.unLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(()->{
            try {
                Thread.sleep(3000);
                fairLock.lock();
                System.out.println(Thread.currentThread().getName()+" test4");
                fairLock.unLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

    }
}
