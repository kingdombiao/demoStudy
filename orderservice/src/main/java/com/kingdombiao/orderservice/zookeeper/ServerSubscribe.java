package com.kingdombiao.orderservice.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-26 14:38
 */
public class ServerSubscribe {

    private static final String zk_url = "127.0.0.1:2181";
    private static final String base_services = "/services";
    private static final String service_name = "/products";

    private static ZooKeeper zooKeeper;

    public static void subscribe(){

        try {
            ZooKeeper zooKeeper = new ZooKeeper(zk_url, 5000, watchedEvent -> {
                if (watchedEvent.getType() == Watcher.Event.EventType.NodeChildrenChanged
                        && watchedEvent.getPath().equals(base_services + service_name)) {
                    updateServiceList();
                }
                updateServiceList();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateServiceList() {
        try {
            List<String> childrenList = zooKeeper.getChildren(base_services + service_name, true);
            List<String> serviceList=new ArrayList<>();
            for (String node : childrenList) {
                byte[] data = zooKeeper.getData(base_services + service_name + "/" + node, false, new Stat());
                String dataS = new String(data);
                System.out.println("host--->"+ dataS);
                serviceList.add(dataS);
                LoadBlance.serviceList=serviceList;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
