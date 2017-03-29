package com.valtech.tp.app1.domain.service.customer;

import com.google.common.collect.Lists;
import com.valtech.tp.app1.domain.model.commun.EntityAlreadyExist;
import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.tp.app1.domain.repository.customer.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private CustomerService service;
    private CustomerRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = mock(CustomerRepository.class);
        service = new CustomerService();
        service.setCustomerRepository(repository);
    }

    @Test
    public void getCustomers() throws Exception {
        when(repository.getCustomers()).thenReturn(Lists.newArrayList());
        assertThat(service.getCustomers()).hasSize(0);

        Customer customer = new Customer("test@valtech.com");
        when(repository.getCustomers()).thenReturn(Lists.newArrayList(customer));

        assertThat(service.getCustomers()).hasSize(1);
        assertThat(service.getCustomers()).contains(customer);

        Customer customer2 = new Customer("test2@valtech.com");
        when(repository.getCustomers()).thenReturn(Lists.newArrayList(customer, customer2));

        assertThat(service.getCustomers()).hasSize(2);
        assertThat(service.getCustomers()).contains(customer, customer2);
    }

    @Test
    public void insertCustomer() throws Exception {
        when(repository.getCustomers()).thenReturn(Lists.newArrayList());
        assertThat(service.getCustomers()).hasSize(0);

        Customer customer = new Customer("test@valtech.com");
        doAnswer(invocationOnMock -> when(repository.getCustomers()).thenReturn(Lists.newArrayList(customer)))
                .when(repository).insertCustomer(customer);

        assertThat(service.getCustomers()).hasSize(0);
        service.insertCustomer(customer);
        assertThat(service.getCustomers()).hasSize(1);
        assertThat(service.getCustomers().get(0)).isEqualTo(customer);

        doThrow(new EntityAlreadyExist("")).when(repository).insertCustomer(customer);
    }
}
