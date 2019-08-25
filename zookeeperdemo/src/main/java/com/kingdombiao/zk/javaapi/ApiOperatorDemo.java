package com.kingdombiao.zk.javaapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ApiOperatorDemo implements Watcher {

    private static final String URL = "127.0.0.1:2181";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static final Stat STAT = new Stat();

    private static ZooKeeper zooKeeper;


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper(URL, 5000, new ApiOperatorDemo());
        countDownLatch.await();

        //创建节点
        // zooKeeper.create("/kingdombiao", "node".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);


        //创建子节点
        //zooKeeper.getData("/kingdombiao/kingdombiao10000000001", new ApiOperatorDemo(), STAT);
       // zooKeeper.create("/kingdombiao/kingdombiao1", "node1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        //TimeUnit.SECONDS.sleep(2);

        //获取节点
        // byte[] data = zooKeeper.getData("/kingdombiao", new ApiOperatorDemo(), STAT);
        // System.out.println("获取节点kingdombiao的数据为：" + new String(data));

        //修改数据
        //zooKeeper.setData("/kingdombiao", "node11".getBytes(), -1);

        //TimeUnit.SECONDS.sleep(2);

        //删除节点
        //zooKeeper.getData("/kingdombiao/kingdombiao10000000001", new ApiOperatorDemo(), STAT);
        //zooKeeper.delete("/kingdombiao/kingdombiao10000000001", -1);
        //TimeUnit.SECONDS.sleep(2);

        //修改子路径
        zooKeeper.getData("/kingdombiao/kingdombiao10000000002", new ApiOperatorDemo(), STAT);
        zooKeeper.setData("/kingdombiao/kingdombiao10000000002","kingdombiao2".getBytes(),-1);
       TimeUnit.SECONDS.sleep(1);

    }


    @Override
    public void process(WatchedEvent watchedEvent) {

        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            System.out.println("zookeeper 连接成功------>" + watchedEvent.getState());
            countDownLatch.countDown();

            if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                try {
                    System.out.println("数据变更触发，路径为：" + watchedEvent.getPath()
                            + ",变更后的值：" + new String(zooKeeper.getData(watchedEvent.getPath(), true, STAT)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (watchedEvent.getType() == Event.EventType.NodeCreated) {
                try {
                    System.out.println("节点创建路径：" + watchedEvent.getType()
                            + ", 节点的值：" + zooKeeper.getData(watchedEvent.getPath(), true, STAT));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
                System.out.println("节点删除路径：" + watchedEvent.getPath());
            } else if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                try {
                    System.out.println("子节点数据变更路径：" + watchedEvent.getPath() + "->节点的值：" +
                            zooKeeper.getData(watchedEvent.getPath(), true, STAT));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}