package com.kingdombiao.rpc.server;

import com.kingdombiao.rpc.client.DemoService;

public class ServerDemo {
    public static void main(String[] args) {
        RegisterCenter registerCenter = new RegisterCenter(6666);
        registerCenter.register(DemoService.class,DemoServiceImpl.class);
        registerCenter.start();
    }
}
