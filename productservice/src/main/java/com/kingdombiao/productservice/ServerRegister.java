package com.kingdombiao.productservice;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-26 10:35
 */
public class ServerRegister {
    private static final String zk_url = "127.0.0.1:2181";
    private static final String base_services = "/services";
    private static final String service_name = "/products";

    private static final CountDownLatch countDownLatch=new CountDownLatch(1);

    public static void register(String address, int port) {

        try {
            ZooKeeper zooKeeper = new ZooKeeper(zk_url, 5000, watchedEvent -> {

                if(watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected){
                    System.out.println("zk 连接成功....."+watchedEvent.getState());
                    countDownLatch.countDown();
                }

            });
            countDownLatch.await();

            Stat stat = zooKeeper.exists(base_services + service_name, false);
            if (stat == null) {
                createParentNode(base_services + service_name,zooKeeper);
            }
            zooKeeper.create(base_services + service_name + "/child", (address + ":" + port).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

    private static void createParentNode(String path,ZooKeeper zooKeeper) throws KeeperException, InterruptedException {

        String newPath = path;
        if (path.startsWith("/")) {
            newPath = path.substring(1);
        }

        if (path.endsWith("/")) {
            newPath = newPath.substring(0, newPath.length() - 1);
        }
        System.out.println("newPath is: " + newPath);
        String[] splitPath = newPath.split("/");

        //逐级创建节点
        String subPath = "";
        for (int i = 0; i < splitPath.length; i++) {
            subPath = String.format("%s/%s", subPath, splitPath[i]);
            if (zooKeeper.exists(subPath, false) == null) {
                zooKeeper.create(subPath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        }
    }
}
