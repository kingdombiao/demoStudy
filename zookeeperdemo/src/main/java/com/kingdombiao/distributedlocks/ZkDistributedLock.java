package com.kingdombiao.distributedlocks;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
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

    private ZooKeeper zooKeeper = null;

    private String rootLock = "/locks";

    /**
     * 当前锁
     */
    private String currentLock;

    /**
     * 等待请一个锁
     */
    private String waitLock;

    private CountDownLatch countDownLatch;


    public ZkDistributedLock() {
        try {
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, this);
            Stat stat = zooKeeper.exists(rootLock, false);
            if (stat == null) {
                zooKeeper.create(rootLock, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化分布式锁异常.......");
        }

    }

    @Override
    public void lock() {
        if (tryLock()) {
            //获得锁成功
            System.out.println(Thread.currentThread().getName() + "--->" + currentLock + "获得锁成功.....");
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
     * 持续阻塞获取锁
     *
     * @param preNode 当前节点的前一个等待节点
     */
    private boolean waitForLock(String preNode) throws KeeperException, InterruptedException {

        //等待锁需要监听上一个节点，设置watcher=true,即每一个有序节点都去监听它的上一个节点
        Stat stat = zooKeeper.exists(preNode, true);
        if (stat != null) {
            //上一个节点存活
            System.out.println(Thread.currentThread().getName() + "-->等待锁" + rootLock + "/" + preNode + "释放.....");
            countDownLatch = new CountDownLatch(1);
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName() + "-->等待后获取锁成功......");
        }
        return true;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            currentLock = zooKeeper.create(rootLock + "/", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName() + "--->" + currentLock + "尝试竞争锁");

            //获取根节点下的所有子节点，不注册监听
            List<String> childrenList = zooKeeper.getChildren(rootLock, false);

            SortedSet<String> sortedSet = new TreeSet<>();
            childrenList.forEach(child -> {
                sortedSet.add(rootLock + "/" + child);
            });

            //获取当前子节点中最小的节点
            String smallestNode = sortedSet.first();
            if (StringUtils.equals(smallestNode, currentLock)) {
                //如果当前节点和最小节点相等，则获取锁成功
                return true;
            }

            //获取比当前节点小的所有节点
            SortedSet<String> lessThenCurrentNode = sortedSet.headSet(currentLock);
            if (CollectionUtils.isNotEmpty(lessThenCurrentNode)) {
                //获取比当前节点小的最后一个节点，设置为等待锁
                waitLock = lessThenCurrentNode.last();
            }
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
        System.out.println(Thread.currentThread().getName()+"-->释放锁 "+currentLock);
        try {
            zooKeeper.delete(currentLock,-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(countDownLatch!=null){
            countDownLatch.countDown();
        }
    }
}
