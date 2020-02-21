package com.kingdombiao.productservice;

import com.kingdombiao.bean.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-26 15:23
 */
@RestController
public class ProductController {
    @RequestMapping("/getProduct/{id}")
    public Object getProduct(HttpServletRequest request, @PathVariable("id") String id){
        return new Product(id,"name:"+request.getLocalPort());
    }


}
