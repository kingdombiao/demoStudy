package com.kingdombiao.thead;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Calculate());

        new Thread(futureTask){
            @Override
            public void run() {
                System.out.println("会执行不.......");
            }
        }.start();

        //do something
        System.out.println("买个蛋糕去......");

        Integer result = futureTask.get();
        System.out.println("获取计算的结果--->"+result);


    }

    static class Calculate implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            System.out.println("正在计算中......");
            TimeUnit.SECONDS.sleep(3);
            return new Random().nextInt(100);
        }
    }
}
