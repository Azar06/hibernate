package com.valtech.tp.app1.domain.model.order;

import com.google.common.collect.ImmutableList;
import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.tp.app1.domain.model.commun.EntityObject;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "Order_table")
public class Order extends EntityObject {
    @Id
    @GeneratedValue
    private Long orderId;

    @NaturalId
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    @NaturalId
    @NotNull
    @ManyToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    //@JoinColumn(name="orderId")
    private Set<OrderLine> orderLines = new HashSet<>();

    private Order() {
    }

    public Order(Date date, Customer customer) {
        this.date = date;
        this.customer = customer;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Date getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    @Override
    public Object getNaturalId() {
        return ImmutableList.builder()
                .add(date == null ? null : date.getTime())
                .add(customer)
                .build();
    }
}
