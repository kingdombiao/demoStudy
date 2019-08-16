package com.kingdombiao.thead.exchanger;

import javax.swing.plaf.TableHeaderUI;
import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-16 17:42
 */
public class ExchangerDemo {


    public static void main(String[] args) {

        Exchanger<Integer> exchanger = new Exchanger<Integer>();
        new Producer("", exchanger).start();
        new Consumer("", exchanger).start();
    }



    static class Producer extends Thread{

        private Exchanger<Integer> exchanger;


        Producer(String name, Exchanger<Integer> exchanger) {
            super("Producer-->" + name);
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for(int i=0;i<1;i++){
                try {
                    TimeUnit.SECONDS.sleep(2);
                    Integer data=new Random().nextInt(100);
                    System.out.println(getName()+"---交换数据前:" + data);
                    Integer data1 = exchanger.exchange(data);
                    System.out.println(getName()+"---交换数据后:" + data1);
                    System.out.println("----------------------------------------------");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread{

        private Exchanger<Integer> exchanger;

        Consumer(String name, Exchanger<Integer> exchanger) {
            super("Consumer-->" + name);
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for(int i=0;i<1;i++){
                try {
                    TimeUnit.SECONDS.sleep(2);
                    Integer data=new Random().nextInt(100);
                    System.out.println(getName()+"---交换数据前:" + data);
                    Integer data1 = exchanger.exchange(data);
                    System.out.println(getName()+"---交换数据后:" + data1);
                    System.out.println("----------------------------------------------");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
