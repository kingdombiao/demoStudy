package com.kingdombiao.productservice;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-26 15:59
 */
public class Product {
    private String id;
    private String name;

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
