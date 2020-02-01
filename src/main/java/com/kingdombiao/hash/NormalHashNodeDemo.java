package com.kingdombiao.hash;

import java.util.HashMap;
import java.util.Map;

public class NormalHashNodeDemo {
    public static void main(String[] args) {
        HashNodeService nodeService = new NormalHashNodeServiceImpl();
        Node node1 = new Node("biao.Node1", "192.168.1.11");
        Node node2 = new Node("biao.Node2", "192.168.2.22");
        Node node3 = new Node("biao.Node3", "192.168.3.33");
        Node node4 = new Node("biao.Node4", "192.168.4.44");
        Node node5 = new Node("biao.Node5", "192.168.5.55");
        Node node6 = new Node("biao.Node6", "192.168.6.66");
        Node node7 = new Node("biao.Node7", "192.168.7.77");
        Node node8 = new Node("biao.Node8", "192.168.8.88");
        nodeService.addNode(node1);
        nodeService.addNode(node2);
        nodeService.addNode(node3);
        nodeService.addNode(node4);
        nodeService.addNode(node5);
        nodeService.addNode(node6);
        nodeService.addNode(node7);
        nodeService.addNode(node8);

        //用于检查数据分布情况
        Map<String, Integer> countMap = new HashMap<>();
        Node node = null;
        for (int i = 1; i <= 100000; i++) {
            String key = String.valueOf(i);
            node = nodeService.lookUpNode(key);
            node.cacheString(key,"cache_test");
            String ipKey = node.getIp();
            Integer count = countMap.get(ipKey);
            if (count == null) {
                count = 1;
                countMap.put(ipKey, count);
            } else {
                count++;
                countMap.put(ipKey, count);
            }
        }
        System.out.println("初始化数据分布情况：" + countMap);


        //统计正常情况下去获取数据
        int hitCount = 0;
        for (int i = 1; i <= 100000; i++) {
            String key = String.valueOf(i);
            node = nodeService.lookUpNode(key);
            if (node != null) {
                String cacheValue = node.getCacheValue(key);
                if(cacheValue!=null){
                    hitCount++;
                }
            }
        }

        double hit = Double.parseDouble(String.valueOf(hitCount)) / Double.parseDouble("100000");
        System.out.println("初始化缓存命中率:" + hit);

        //统计增加一个节点后的 命中率
        Node node9 = new Node("biao.Node9", "192.168.9.99");
        nodeService.addNode(node9);

        hitCount = 0;
        for (int i = 1; i <= 100000; i++) {
            String key = String.valueOf(i);
            node = nodeService.lookUpNode(key);
            if (node != null) {
                String cacheValue = node.getCacheValue(key);
                if(cacheValue!=null){
                    hitCount++;
                }
            }
        }

        hit = Double.parseDouble(String.valueOf(hitCount)) / Double.parseDouble("100000");
        System.out.println("增加一个节点后缓存命中率:" + hit);

    }

}
