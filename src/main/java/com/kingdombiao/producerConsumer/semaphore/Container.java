package com.kingdombiao.producerConsumer.semaphore;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * 容器
 *
 * @author biao
 * @create 2019-08-13 15:26
 */
public class Container {

    Semaphore fullCount = new Semaphore(0); //表示容器是否为空
    Semaphore emptyCount = new Semaphore(10); //表示容器是否已满
    Semaphore mutex = new Semaphore(1); //互斥

    private LinkedList<Integer> list = new LinkedList<Integer>();

    public void put(Integer value) {
        try {
            emptyCount.acquire();
            mutex.acquire();

            list.add(value);
            System.out.println("producer--" + Thread.currentThread().getName() + "-->put:" + value + "--->size:" + list.size());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            fullCount.release();
        }
    }


    public Integer take() {
        Integer value = 0;
        try {
            fullCount.acquire();
            mutex.acquire();
            value = list.removeFirst();

            System.out.println("consumer--" + Thread.currentThread().getName() + "-->take:" + value + "--->size:" + list.size());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            emptyCount.release();
        }
        return value;
    }


}
