package com.valtech.tp.app1.domain.repository.customer;


import com.valtech.tp.app1.domain.model.address.Address;
import com.valtech.tp.app1.domain.model.customer.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class CustomerRepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private CustomerRepository repo;

    private static String lastName = "Zaragoza";
    private static String firstName = "Arnaud";
    private static String email = "arnaud.zaragoza@valtech.com";

    private static String street = "148 Rue de Courcelles";
    private static String postCode = "75017";
    private static String city = "Paris";
    private static String country = "France";

    public static Address createDummyAddress() {
        Address address = new Address();
        address.setStreet(street);
        address.setPostCode(postCode);
        address.setCity(city);
        address.setCountry(country);
        return address;
    }

    public static Customer createDummyCustomer() {
        Customer customer = new Customer(email);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddress(createDummyAddress());
        return customer;
    }

    @Test
    public void insertCustomer() throws Exception {
        Customer dummyCustomer = createDummyCustomer();
        repo.insertCustomer(dummyCustomer);
        em.clear();

        Customer foundCustomer = em.find(Customer.class, dummyCustomer.getId());
        assertThat(foundCustomer).isEqualTo(dummyCustomer);
    }

    @Test
    public void insertAndFind_withEntityManager() throws Exception {
        Customer customer = createDummyCustomer();
        em.persist(customer);

        em.clear();
        Customer foundCustomer = em.find(Customer.class, customer.getId());

        assertThat(foundCustomer).isEqualTo(customer);
        assertThat(foundCustomer.getId()).isEqualTo(customer.getId());
        assertThat(foundCustomer.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(foundCustomer.getAddress()).isEqualTo(customer.getAddress());
    }

    @Test
    public void findById_with_hql() throws Exception {
        Customer customer = createDummyCustomer();
        em.persist(customer);
        em.clear();

        Customer foundCustomer = repo.findById(customer.getId());
        assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    public void findByEmail_hql() throws Exception {
        Customer customer = createDummyCustomer();
        em.persist(customer);
        em.clear();

        Customer foundCustomer = repo.findByEmail_hql(customer.getEmail());
        assertThat(foundCustomer).isEqualTo(customer);
    }


    @Test
    public void findByEmail_criteria() throws Exception {
        Customer customer = createDummyCustomer();
        em.persist(customer);
        em.clear();

        Customer foundCustomer = repo.findByEmail_criteria(customer.getEmail());
        assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    public void findByEmail_criteria_jpa() throws Exception {
        Customer customer = createDummyCustomer();
        em.persist(customer);
        em.clear();

        Customer foundCustomer = repo.findByEmail_criteria_jpa(customer.getEmail());
        assertThat(foundCustomer).isEqualTo(customer);
    }
}
