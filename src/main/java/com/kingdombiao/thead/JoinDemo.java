package com.kingdombiao.thead;

import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-14 18:02
 */
public class JoinDemo extends Thread{
    int i;
    Thread previousThread; //上一个线程
    public JoinDemo(Thread previousThread,int i){
        this.previousThread=previousThread;
        this.i=i;
    }
    @Override
    public void run() {
       try {
          // previousThread.join();
           TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("num:"+i);
    }
    public static void main(String[] args) throws InterruptedException {

        Thread previousThread=Thread.currentThread();
        for(int i=0;i<2;i++){
            JoinDemo joinDemo=new JoinDemo(previousThread,i);
            joinDemo.start();
            previousThread=joinDemo;
           joinDemo.join();
        }

        TimeUnit.SECONDS.sleep(5);
        System.out.println("ddad");
    }
}
