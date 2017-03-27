package com.valtech.tp.app1.domain.repository.order;

import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.tp.app1.domain.model.order.Order;
import com.valtech.tp.app1.domain.repository.commun.DomainRepository;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository extends DomainRepository {

    public void insert(Order order) {
        getEntityManager().persist(order);
    }

    public Order getOrder(Long id) {
        return getEntityManager().find(Order.class, id);
    }

    public Order findOrderAndOrderLines(Long id) {
        return (Order) getCurrentHbnSession()
                .createCriteria(Order.class)
                .add(Restrictions.idEq(id))
                .setFetchMode("orderLines", FetchMode.JOIN)
                .uniqueResult();
    }

    public Order findOrderAndOrderLinesUsingHQL(Long id) {
        return (Order) getCurrentHbnSession()
                .createQuery("from Order o left join fetch o.orderLines where o.id = :id")
                .setParameter("id", id)
                .uniqueResult();
    }

    public Order findOrdersWithCustomerContainingProductName(Customer customer, String productName) {
        return (Order) getCurrentHbnSession()
                .createCriteria(Order.class)
                .add(Restrictions.eq("customer.id", customer.getId()))
                .createAlias("orderLines", "ol")
                .createAlias("ol.product", "p")
                .add(Restrictions.ilike("p.name", productName.replace('*', '%')))
                .uniqueResult();
    }
}
