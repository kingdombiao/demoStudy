package com.kingdombiao.producerConsumer.waitAndnotify;

import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * 容器
 *
 * @author biao
 * @create 2019-08-13 15:26
 */
public class Container {

    private LinkedList<Integer> list = new LinkedList<Integer>();

    private int capacity = 10;

    public void put(int value) {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (this) {
                while (list.size() == capacity) {
                    System.out.println("container is full,waiting....   ");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("producer--" + Thread.currentThread().getName() + "--put: " + value);
                list.add(value);
                notifyAll();
            }
        }
    }


    public Integer take(){
        Integer value;
        while(true){
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (this){
                while(list.size()==0){
                    System.out.println("container is empty,waiting ...");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                value = list.removeFirst();
                System.out.println("consumer---"+Thread.currentThread().getName()+"---take: "+value);
                notifyAll();
            }
        }

    }


}
