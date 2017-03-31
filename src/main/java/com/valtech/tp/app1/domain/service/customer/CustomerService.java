package com.valtech.tp.app1.domain.service.customer;

import com.google.common.annotations.VisibleForTesting;
import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.tp.app1.domain.repository.customer.CustomerRepository;
import com.valtech.tp.app1.domain.service.commun.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService extends DomainService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getCustomers() {
        return customerRepository.getCustomers();
    }

    public Customer insertCustomer(Customer customer) {
        customerRepository.insertCustomer(customer);
        return customer;
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail_criteria(email);
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void deleteCustomerByEmail(String email) {
        customerRepository.deleteCustomerByEmail(email);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteCustomerById(id);
    }
}
