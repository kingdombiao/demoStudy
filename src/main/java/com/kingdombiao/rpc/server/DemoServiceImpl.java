package com.kingdombiao.rpc.server;

import com.kingdombiao.rpc.client.DemoService;

public class DemoServiceImpl  implements DemoService {
    @Override
    public String sayHello(String arg) {
        return "hello " +arg+",my name is james";
    }
}
