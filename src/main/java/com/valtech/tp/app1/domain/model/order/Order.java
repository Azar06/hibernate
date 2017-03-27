package com.valtech.tp.app1.domain.model.order;

import com.google.common.collect.ImmutableList;
import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.tp.app1.domain.model.commun.DomainEntity;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "Order_")
public class Order extends DomainEntity {
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @NaturalId
    @NotNull
    @ManyToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private Set<OrderLine> orderLines = new HashSet<>();

    private Order() {
    }

    public Order(Date date, Customer customer) {
        this.date = date;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
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
