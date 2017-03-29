package com.valtech.tp.app1.domain.repository.customer;


import com.valtech.tp.app1.domain.model.commun.EntityAlreadyExist;
import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.tp.app1.domain.repository.commun.DomainRepository;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CustomerRepository extends DomainRepository {

    public Customer findById(Long id) {
        return getEntityManager().find(Customer.class, id);
    }

    public Customer findByEmail_hql(String email) {
        return (Customer) getCurrentHbnSession()
                .createQuery("from Customer where email = :email")
                .setParameter("email", email)
                .uniqueResult();
    }

    public Customer findByEmail_criteria(String email) {
        return (Customer) getCurrentHbnSession().createCriteria(Customer.class)
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
        if(findByEmail_criteria(customer.getEmail()) == null) {
            getEntityManager().persist(customer);
        }
        else {
            throw new EntityAlreadyExist("The reference is already used.");
        }
    }

    public List<Customer> getCustomers() {
        return getCurrentHbnSession().createCriteria(Customer.class).list();
    }
}
