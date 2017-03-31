package com.valtech.tp.app1.domain.repository.customer;


import com.valtech.tp.app1.domain.model.commun.EntityAlreadyExist;
import com.valtech.tp.app1.domain.model.commun.EntityNotFound;
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
        Customer customer = (Customer) getCurrentHbnSession().createCriteria(Customer.class)
                .add(Restrictions.like("email", email))
                .uniqueResult();

        if(customer == null) {
            throw new EntityNotFound("The Customer was not found.");
        }

        return customer;
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
        try {
            findByEmail_criteria(customer.getEmail());
            throw new EntityAlreadyExist("The reference is already used.");
        }
        catch(EntityNotFound ex) {
            getEntityManager().persist(customer);
        }
    }

    public List<Customer> getCustomers() {
        return getCurrentHbnSession().createCriteria(Customer.class).list();
    }

    public void deleteCustomerByEmail(String email) {
        Customer customer = findByEmail_criteria(email);
        getEntityManager().remove(customer);
    }

    public void deleteCustomerById(Long id) {
        Customer customer = findById(id);
        getEntityManager().remove(customer);
    }
}
