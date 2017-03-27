package com.valtech.tp.app1.domain.model.order;

import com.google.common.collect.ImmutableList;
import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.utils.model.EntityObject;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    //@NaturalId
    //@NotNull
    @ManyToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="orderId")
    private List<OrderLine> orderLines = new ArrayList<>();

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

    public List<OrderLine> getOrderLines() {
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
