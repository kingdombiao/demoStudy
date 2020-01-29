package com.kingdombiao.rpc.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class ClientDemo {
    public static void main(String[] args) {
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 6666);
        DemoService demoService = RpcClient.getRemoteProxyObj(DemoService.class, socketAddress);
        System.out.println(demoService.sayHello("tom"));
    }
}
