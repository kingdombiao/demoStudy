package com.kingdombiao.hash;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private String nodeName;
    private String ip;

    private Map<String,String> cacheMap=new HashMap<>();

    public void cacheString(String key,String value){
        cacheMap.put(key,value);
    }

    public String getCacheValue(String key){
        return cacheMap.get(key);
    }

    public Node(String nodeName, String ip) {
        this.nodeName = nodeName;
        this.ip = ip;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
