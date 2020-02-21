package com.kingdombiao.orderservice.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingdombiao.bean.Order;
import com.kingdombiao.bean.Product;
import com.kingdombiao.orderservice.zookeeper.LoadBlance;
import com.kingdombiao.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @Reference
    private ProductService productService;

    private LoadBlance loadBlance=new LoadBlance();

    @RequestMapping("/getOrder/{id}")
    public Object getOrder(@PathVariable("id") String id){
        Product product = restTemplate.getForObject("http://" + loadBlance.choseServiceHost() + "getProduct/" + id, Product.class);
        return new Order(id,"oderName",product);
    }

    @RequestMapping("/getProductInfo/{productId}")
    public Product getProductInfo(Integer productId){
        Product product = productService.getProductInfo(productId);
        return  product;
    }


}
