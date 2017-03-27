package com.valtech.tp.app1.domain.repository.customer;


import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.util.model.DomainRepository;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class CustomerRepository extends DomainRepository {

    public Customer findById(Long id) {
        return getEntityManager().find(Customer.class, id);
    }

    public Customer findByEmail_hql(String email) {
        return (Customer) getHibernateSession()
                .createQuery("from Customer where email = :email")
                .setParameter("email", email)
                .uniqueResult();
    }


    private Session getHibernateSession() {
        return getEntityManager().unwrap(Session.class);
    }

    public Customer findByEmail_criteria(String email) {
        return (Customer) getHibernateSession().createCriteria(Customer.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }


    public Customer findByEmail_criteria_jpa(String email) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
        return (Customer) getEntityManager().createQuery(criteriaQuery
                .select(customerRoot)
                .where(criteriaBuilder.equal(customerRoot.get("email"), email)))
                .getSingleResult();
    }


    public void insertCustomer(Customer customer) {
        getEntityManager().persist(customer);
    }
}
