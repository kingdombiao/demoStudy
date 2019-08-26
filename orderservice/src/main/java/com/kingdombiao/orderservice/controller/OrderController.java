package com.kingdombiao.orderservice.controller;

import com.kingdombiao.orderservice.bean.Order;
import com.kingdombiao.orderservice.bean.Product;
import com.kingdombiao.orderservice.zookeeper.LoadBlance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-26 15:05
 */
@RestController
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    private LoadBlance loadBlance=new LoadBlance();

    @RequestMapping("/getOrder/{id}")
    public Object getOrder(@PathVariable("id") String id){
        Product product = restTemplate.getForObject("http://" + loadBlance.choseServiceHost() + "getProduct/" + id, Product.class);
        return new Order(id,"oderName",product);
    }


}
