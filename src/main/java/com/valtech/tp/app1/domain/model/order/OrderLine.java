package com.valtech.tp.app1.domain.model.order;

import com.google.common.collect.ImmutableList;
import com.valtech.tp.app1.domain.model.product.Product;
import com.valtech.util.model.EntityObject;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;


@Entity
public class OrderLine extends EntityObject {
    @Id
    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @Id
    @NaturalId
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    private Integer quantity;

    private OrderLine() {
    }

    public OrderLine(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public Object getNaturalId() {
        return ImmutableList.builder()
                .add(order)
                .add(product)
                .build();
    }
}
