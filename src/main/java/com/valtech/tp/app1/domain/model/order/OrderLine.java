package com.valtech.tp.app1.domain.model.order;

import com.google.common.collect.ImmutableList;
import com.valtech.tp.app1.domain.model.product.Product;
import com.valtech.tp.app1.domain.model.commun.EntityObject;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class OrderLine extends EntityObject {
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @NotNull
    @ManyToOne
    private Order order;

    @NaturalId
    @NotNull
    @ManyToOne
    private Product product;

    private Integer quantity;

    private OrderLine() {
    }

    public OrderLine(Long id) {
        this.id = id;
    }

    public OrderLine(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    public Long getId() {
        return id;
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
