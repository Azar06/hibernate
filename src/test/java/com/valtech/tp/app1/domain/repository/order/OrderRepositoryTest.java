package com.valtech.tp.app1.domain.repository.order;

import com.google.common.collect.ImmutableList;
import com.valtech.tp.app1.domain.model.address.Address;
import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.tp.app1.domain.model.order.Order;
import com.valtech.tp.app1.domain.model.order.OrderLine;
import com.valtech.tp.app1.domain.model.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class OrderRepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private OrderRepository repo;


    private Date date = new Date(11111);

    private Customer customer = createDummyCustomer();
    private List<Product> products = ImmutableList.<Product>builder()
            .add(this.createDummyProduct("HP"))
            .add(this.createDummyProduct("CD"))
            .build();


    private String lastName = "Zaragoza";
    private String firstName = "Arnaud";
    private String email = "arnaud.zaragoza@valtech.com";

    private String street = "148 Rue de Courcelles";
    private String postCode = "75017";
    private String city = "Paris";
    private String country = "France";

    private Address createDummyAddress() {
        Address address = new Address();
        address.setStreet(street);
        address.setPostCode(postCode);
        address.setCity(city);
        address.setCountry(country);
        return address;
    }

    private Customer createDummyCustomer() {
        Customer customer = new Customer(email);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddress(createDummyAddress());
        return customer;
    }

    private String name = "MyName";
    private Double price = 10.9d;
    private String description = "description";

    private Product createDummyProduct(String ref) {
        Product product = new Product(ref);
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        return product;
    }

    @Test
    public void insert() throws Exception {
        Order order = new Order(date, createDummyCustomer());
        em.persist(order.getCustomer());
        repo.insert(order);
        em.clear();

        Order foundOrder = em.find(Order.class, order.getId());

        assertThat(foundOrder.getDate().getTime()).isEqualTo(order.getDate().getTime());
        assertThat(foundOrder).isEqualTo(order);
        assertThat(foundOrder.getCustomer()).isEqualTo(this.createDummyCustomer());
    }

    @Test
    public void insert_withOneOrderLine() throws Exception {
        Customer customer = createDummyCustomer();
        Product product = createDummyProduct("MyRef");
        em.persist(customer);
        em.persist(product);

        Order order = new Order(date, customer);
        OrderLine orderLine = new OrderLine(order, product);
        order.getOrderLines().add(orderLine);

        repo.insert(order);

        em.flush();
        em.clear();

        // check the asserts
        Order foundOrder = em.find(Order.class, order.getId());
        assertThat(foundOrder).isEqualTo(order);
        assertThat(foundOrder.getOrderLines()).isNotNull();
        assertThat(foundOrder.getOrderLines()).hasSize(1);
        assertThat(foundOrder.getOrderLines()).containsExactly(orderLine);
    }

    @Test
    public void insert_withOneOrderLine_addedAfterSave() throws Exception {
        Customer customer = createDummyCustomer();
        Product product = createDummyProduct("MyRef");
        em.persist(customer);
        em.persist(product);

        Order order = new Order(date, customer);

        repo.insert(order);

        // update order after the persist
        OrderLine orderLine = new OrderLine(order, product);
        order.getOrderLines().add(orderLine);

        em.flush();
        em.clear();

        // check the asserts
        Order foundOrder = em.find(Order.class, order.getId());
        assertThat(foundOrder).isEqualTo(order);
        assertThat(foundOrder.getOrderLines()).isNotNull();
        assertThat(foundOrder.getOrderLines()).hasSize(1);
        assertThat(foundOrder.getOrderLines()).containsExactly(orderLine);
    }

    @Test
    public void remove_orderLine() throws Exception {
        Customer customer = createDummyCustomer();
        Product product = createDummyProduct("MyRef");
        em.persist(customer);
        em.persist(product);

        Order order = new Order(date, customer);
        OrderLine orderLine = new OrderLine(order, product);
        order.getOrderLines().add(orderLine);

        repo.insert(order);

        em.flush();
        em.clear();

        // reload the class because the clear disconnect the order from hibernate
        Order foundOrder = em.find(Order.class, order.getId());
        boolean hasRemovedSomething = foundOrder.getOrderLines().remove(new OrderLine(order, product));
        assertThat(hasRemovedSomething).isTrue();

        em.flush();
        em.clear();

        // reload the class because the clear disconnect the order from hibernate
        foundOrder = em.find(Order.class, order.getId());
        assertThat(foundOrder.getOrderLines()).isEmpty();
    }

    @Test
    public void findOrdersWithCustomerContainingProductName() throws Exception {
        Customer customer = createDummyCustomer();
        em.persist(customer);

        Product product = createDummyProduct("MyRef");
        em.persist(product);

        Order order = new Order(date, customer);
        OrderLine orderLine = new OrderLine(order, product);
        order.getOrderLines().add(orderLine);
        repo.insert(order);

        em.flush();
        em.clear();

        Order foundOrder = repo.findOrdersWithCustomerContainingProductName(customer, "myname");

        assertThat(foundOrder).isEqualTo(order);
    }

    @Test
    public void findOrderAndOrderLines() throws Exception {
        Customer customer = createDummyCustomer();
        em.persist(customer);

        Product product = createDummyProduct("MyRef");
        em.persist(product);

        Order order = new Order(date, customer);
        OrderLine orderLine = new OrderLine(order, product);
        order.getOrderLines().add(orderLine);
        repo.insert(order);

        em.flush();
        em.clear();

        Order foundOrder = repo.findOrderAndOrderLines(order.getId());

        assertThat(foundOrder).isEqualTo(order);
        assertThat(order.getOrderLines()).hasSize(1);
        // throw a Lazy...Exception if the orderLines are not loaded
    }

    @Test
    public void findOrderAndOrderLinesUsingHQL() throws Exception {
        Customer customer = createDummyCustomer();
        em.persist(customer);

        Product product = createDummyProduct("MyRef");
        em.persist(product);

        Order order = new Order(date, customer);
        OrderLine orderLine = new OrderLine(order, product);
        order.getOrderLines().add(orderLine);
        repo.insert(order);

        em.flush();
        em.clear();

        Order foundOrder = repo.findOrderAndOrderLinesUsingHQL(order.getId());

        em.clear();

        assertThat(foundOrder).isEqualTo(order);
        assertThat(foundOrder.getOrderLines()).hasSize(1);
        // throw a Lazy...Exception if the orderLines are not loaded
    }

    @Test(expected = Exception.class)
    public void insert_withoutExistingCustomer() throws Exception {
        Order order = new Order(date, createDummyCustomer());
        repo.insert(order);
    }

    @Test
    public void getOrder() throws Exception {
        Order order = new Order(date, createDummyCustomer());
        em.persist(order.getCustomer());
        em.persist(order);
        em.clear();

        Order foundOrder = repo.getOrder(order.getId());
        assertThat(foundOrder).isEqualTo(order);
        assertThat(foundOrder.getCustomer()).isEqualTo(this.createDummyCustomer());
    }
}
