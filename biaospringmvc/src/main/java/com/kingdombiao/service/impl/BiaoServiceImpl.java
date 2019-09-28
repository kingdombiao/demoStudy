package com.kingdombiao.service.impl;

import com.kingdombiao.annotation.KingdomBiaoService;
import com.kingdombiao.service.BiaoService;

@KingdomBiaoService("biaoServiceImpl")
public class BiaoServiceImpl implements BiaoService {
    @Override
    public String query(String name, String age) {

        return "{name=" + name + ",age=" + age + "}";
    }

    @Override
    public String insert(String param) {
        return "insert successful.....";
    }

    @Override
    public String update(String param) {
        return "update successful.....";
    }
}
