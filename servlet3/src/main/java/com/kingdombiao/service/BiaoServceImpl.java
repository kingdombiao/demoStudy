package com.kingdombiao.service;

import org.springframework.stereotype.Service;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author biao
 * @create 2019-09-26 10:23
 */
@Service
public class BiaoServceImpl implements BiaoService {
    @Override
    public String getBuy(String orderId) {
        return "orderId:"+orderId;
    }
}
