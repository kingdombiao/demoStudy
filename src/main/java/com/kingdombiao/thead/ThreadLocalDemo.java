package com.kingdombiao.thead;

import javax.sound.midi.Soundbank;

/**
 * 描述:
 * threadLocal
 *
 * @author biao
 * @create 2019-08-15 10:38
 */
public class ThreadLocalDemo {

    public static void main(String[] args) {
        ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

       Thread thread1 = new Thread(){
            @Override
            public void run() {
                stringThreadLocal.set("threadName===>"+Thread.currentThread().getName());
                stringThreadLocal.set("threadName===>"+Thread.currentThread().getName());
                System.out.println(this.getName()+" thread get the value:"+stringThreadLocal.get());

            }
        };

        thread1.start();

       /* System.out.println("main线程调用set方法之前："+stringThreadLocal.get());
        stringThreadLocal.set("main 线程set的值");
        System.out.println("main线程调用set方法之后："+stringThreadLocal.get());*/
    }


}
