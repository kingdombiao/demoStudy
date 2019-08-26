package com.kingdombiao.distributedlocks;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 描述:
 * 使用zk原生api实现分布式锁
 *
 * @author biao
 * @create 2019-08-26 17:48
 */
public class ZkDistributedLock implements Lock, Watcher {

    private ZooKeeper zooKeeper=null;

    private String rootLock="/locks";

    /**当前锁*/
    private String currentLock;

    /**等待请一个锁*/
    private String waitLock;

    private CountDownLatch countDownLatch;


    public ZkDistributedLock() {
        try {
            zooKeeper=new ZooKeeper("127.0.0.1:2181",5000,this);
            Stat stat = zooKeeper.exists(rootLock, false);
            if(stat==null){
                zooKeeper.create(rootLock,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化分布式锁异常.......");
        }

    }

    @Override
    public void lock() {
        if(tryLock()){
            //获得锁成功
            System.out.println(Thread.currentThread().getName()+"--->"+currentLock+"获得锁成功.....");
            return;
        }

        try {
            waitForLock(waitLock);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     *持续阻塞获取锁
     * @param preNode 当前节点的前一个等待节点
     */
    private boolean waitForLock(String preNode) throws KeeperException, InterruptedException {

        Stat stat = zooKeeper.exists(preNode, true);
        if(stat!=null){
            //上一个节点存活
            System.out.println(Thread.currentThread().getName()+"-->等待锁"+rootLock+"/"+preNode+"释放.....");
            countDownLatch=new CountDownLatch(1);
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName()+"-->等待后获取锁成功......");
        }

        return true;

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {

        try {
            currentLock= zooKeeper.create(rootLock + "/", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName()+"--->"+currentLock+"尝试竞争锁");

            //获取根节点下的所有子节点
            zooKeeper.getChildren(rootLock,false);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }
}
