package com.valtech.tp.app1.domain.repository.order;

import com.valtech.tp.app1.domain.model.order.Order;
import com.valtech.util.model.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class OrderRepository extends DomainRepository {

    public void insert(Order order) {
        getEntityManager().persist(order);
    }

    public Order getOrder(Long id) {
        return getEntityManager().find(Order.class, id);
    }
}
