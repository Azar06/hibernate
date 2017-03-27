package com.valtech.tp.app1.domain.repository.order;

import com.valtech.tp.app1.domain.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class OrderRepository {
    @Autowired
    private EntityManager em;

    public void insert(Order order) {
        em.persist(order);
    }

    public Order getOrder(Long id) {
        return em.find(Order.class, id);
    }
}
