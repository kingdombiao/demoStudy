package com.kingdombiao.orderservice.bean;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-26 15:16
 */
public class Order {

    private String id;
    private String name;

    private Product product;

    public Order(String id, String name, Product product) {
        this.id = id;
        this.name = name;
        this.product = product;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
