package com.kingdombiao.productservice.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kingdombiao.bean.Product;
import com.kingdombiao.service.ProductService;
import org.springframework.stereotype.Component;

@Service //暴露服务
@Component
public class ProductServiceImpl implements ProductService {

    @Override
    public Product getProductInfo(Integer productId) {
        return new Product(String.valueOf(productId),"dubbo测试");
    }
}
