package com.kingdombiao.threadCommunication;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-13 14:26
 */
public class WaitNotifyTest {

    public static void main(String[] args) {
        Object object = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }


}
