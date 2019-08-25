package com.kingdombiao.zk.javaapi;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CreateConnectionDemo {

    private static final String URL="127.0.0.1:2181";

    private static CountDownLatch countDownLatch=new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {

        ZooKeeper zooKeeper = new ZooKeeper(URL, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

                if(watchedEvent.getState()==Event.KeeperState.SyncConnected){
                    System.out.println("连接成功------>"+watchedEvent.getState());
                    countDownLatch.countDown();
                }

            }
        });

        countDownLatch.await();
        System.out.println("连接状态--->"+zooKeeper.getState());

    }
}
