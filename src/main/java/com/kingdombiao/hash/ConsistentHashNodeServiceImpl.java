package com.kingdombiao.hash;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.CRC32;

/**
 * 分布式数据分布算法(一致性Hash)
 */
public class ConsistentHashNodeServiceImpl implements HashNodeService{

    //引入虚拟节点，保证数据分布均匀
    private static final int VIRTUAL_NODE_NUM=64;

    //存储节点列表
    private List<Node> nodes=new ArrayList<>();

    //数据存储在具体的节点上
    private TreeMap<Long,Node> nodeMap=new TreeMap<>();

    /**
     * 集群中增加数据存储节点
     *
     * @param node
     */
    @Override
    public void addNode(Node node) {
        nodes.add(node);
        Long hashKey = hash(node.getIp());
        nodeMap.put(hashKey,node);
        //同一个node节点在分散64个虚拟节点
        for (int i = 1; i <= VIRTUAL_NODE_NUM; i++) {
            Long crc32Hash = hash(node.getIp() + "@" + i);
            nodeMap.put(crc32Hash,node);
        }
    }

    /**
     * 数据存储时查找具体使用哪个节点来存储
     *
     * @param key
     * @return
     */
    @Override
    public Node lookUpNode(String key) {
        Long hashKey = hash(key);
        Node validNode = findValidNode(hashKey);
        if(validNode==null){
            return findValidNode(0L);
        }
        return validNode;
    }

    /**
     * 顺时针找最近的存储节点
     * @param crcHashKey
     */
    private Node findValidNode(Long crcHashKey) {
        Map.Entry<Long, Node> entry = nodeMap.ceilingEntry(crcHashKey);
        if(entry!=null){
            return entry.getValue();
        }
        return null;
    }

    /**
     * hash算法
     *
     * @param key
     * @return
     */
    @Override
    public Long hash(String key) {
        CRC32 crc32 = new CRC32();
        crc32.update(key.getBytes());
        return crc32.getValue();
    }

    /**
     * 模拟意外情况断掉一个节点，用于测试缓存命中率
     *
     * @param node
     */
    @Override
    public void removeNodeUnexpected(Node node) {
        nodes.remove(node);
        Long hash = hash(node.getIp());
        nodeMap.remove(hash);
        //删除节点对应的虚拟节点也一并删除
        for (int i = 1; i <= VIRTUAL_NODE_NUM; i++) {
            Long crc32Hash = hash(node.getIp() + "-" + i);
            nodeMap.remove(crc32Hash);
        }

    }
}
