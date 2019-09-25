package com.kingdombiao.lock;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 描述:
 * 分布式锁这种实现方式会产生 “羊群效应”
 *
 * @author biao
 * @create 2019-09-25 14:08
 */
public class OrderServiceDemo implements Runnable{

    private OrderNumGenerator orderNumGenerator=new OrderNumGenerator();

    //方式一
    //private Lock lock=new ZkDistrbuteLock();

    //方式二
    private Lock lock=new ZkDistrbuteLockPlus();



    @Override
    public void run() {
        getNumber();

    }

    private void getNumber() {

        try {
            lock.getLock();
            String number = orderNumGenerator.getNumber();
            System.out.println(Thread.currentThread().getName()+",生成订单ID:"+number);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unLock();
        }

    }


    public static void main(String[] args) {
        for (int i=0;i<=70;i++){
            new Thread(new OrderServiceDemo()).start();
        }

    }


}
