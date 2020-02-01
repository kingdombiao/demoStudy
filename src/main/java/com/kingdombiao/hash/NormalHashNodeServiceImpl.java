package com.kingdombiao.hash;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

/**
 * 普通hash算法
 */
public class NormalHashNodeServiceImpl implements HashNodeService{

    private List<Node> nodes=new ArrayList<>();

    /**
     * 集群中增加数据存储节点
     *
     * @param node
     */
    @Override
    public void addNode(Node node) {
        this.nodes.add(node);
    }

    /**
     * 数据存储时查找具体使用哪个节点来存储
     *
     * @param key
     * @return
     */
    @Override
    public Node lookUpNode(String key) {
        Long hash = hash(key);
        int index=(int)(hash%nodes.size());
        return nodes.get(index);
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
    }
}
