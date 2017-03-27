package com.valtech.tp.app1.domain.repository.order;

import com.valtech.tp.app1.domain.model.order.Order;
import com.valtech.tp.app1.domain.repository.commun.DomainRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository extends DomainRepository {

    public void insert(Order order) {
        getEntityManager().persist(order);
    }

    public Order getOrder(Long id) {
        return getEntityManager().find(Order.class, id);
    }
}
