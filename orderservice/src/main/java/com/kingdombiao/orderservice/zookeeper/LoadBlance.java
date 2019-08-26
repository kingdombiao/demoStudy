package com.kingdombiao.orderservice.zookeeper;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-26 14:56
 */
public class LoadBlance {

    public static List<String> serviceList;


    public String choseServiceHost(){

        String result="";

        if(!CollectionUtils.isEmpty(serviceList)){
            int index = new Random().nextInt(serviceList.size());
            result= serviceList.get(index);
        }
        return result;
    }

}
