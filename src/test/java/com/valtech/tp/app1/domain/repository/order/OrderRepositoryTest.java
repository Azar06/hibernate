package com.valtech.tp.app1.domain.repository.order;

import com.google.common.collect.ImmutableList;
import com.valtech.tp.app1.domain.model.address.Address;
import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.tp.app1.domain.model.order.Order;
import com.valtech.tp.app1.domain.model.order.OrderLine;
import com.valtech.tp.app1.domain.model.product.Product;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Ignore;
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

    private Order createOrder() {
        Order order = new Order(date, createDummyCustomer());
        return order;
    }

    @Test
    public void insert() throws Exception {
        Order order = createOrder();
        em.persist(order.getCustomer());
        repo.insert(order);
        em.clear();

        Order foundOrder = em.find(Order.class, order.getOrderId());

        assertThat(foundOrder.getDate().getTime()).isEqualTo(order.getDate().getTime());
        assertThat(foundOrder).isEqualTo(order);
        assertThat(foundOrder.getCustomer()).isEqualTo(this.createDummyCustomer());
    }

    @Test
    @Ignore
    public void insert_withOneOrderLine() throws Exception {
        // modelisation
        Order order = createOrder();
        Product product = createDummyProduct("MyRef");
        OrderLine orderLine = new OrderLine(order, product);
        order.getOrderLines().add(orderLine);

        // persist the model
        em.persist(order.getCustomer());
        em.persist(product);
        repo.insert(order);

        em.clear();

        // check the asserts
        Order foundOrder = em.find(Order.class, order.getOrderId());
        assertThat(foundOrder).isEqualTo(order);
        assertThat(foundOrder.getOrderLines()).isNotNull();
        assertThat(foundOrder.getOrderLines()).hasSize(1);
        assertThat(foundOrder.getOrderLines()).containsExactly(orderLine);
    }

    @Test
    public void insert_withOneOrderLine_addedAfterSave() throws Exception {
        // modelisation
        Order order = createOrder();
        Product product = createDummyProduct("MyRef");

        // persist the model
        em.persist(order.getCustomer());
        em.persist(product);

        OrderLine orderLine = new OrderLine(order, product);
        order.getOrderLines().add(orderLine);

        repo.insert(order);

        em.flush();
        em.clear();

        // check the asserts
        Order foundOrder = em.find(Order.class, order.getOrderId());
        assertThat(foundOrder).isEqualTo(order);
        assertThat(foundOrder.getOrderLines()).isNotNull();
        assertThat(foundOrder.getOrderLines()).hasSize(1);
        assertThat(foundOrder.getOrderLines()).containsExactly(orderLine);

        foundOrder.getOrderLines().iterator().next().setQuantity(700);

        em.flush();

        assertThat(foundOrder.getOrderLines().remove(new OrderLine(order, product))).isTrue();
        em.flush();
        em.clear();

        foundOrder = em.find(Order.class, order.getOrderId());
        assertThat(foundOrder.getOrderLines()).isEmpty();
    }

    @Test
    public void find() throws Exception {
        // modelisation
        Order order = createOrder();
        Product product = createDummyProduct("MyRef");

        // persist the model
        em.persist(order.getCustomer());
        em.persist(product);

        OrderLine orderLine = new OrderLine(order, product);
        order.getOrderLines().add(orderLine);

        repo.insert(order);

        em.flush();
        em.clear();

        Session session = em.unwrap(Session.class);
        order = (Order) session
                .createCriteria(Order.class)
                .add(Restrictions.eq("customer.id", order.getCustomer().getId()))
                .createAlias("orderLines", "ol")
                .createAlias("ol.product", "p")
                .add(Restrictions.ilike("p.name", "myname"))
                .uniqueResult();

        System.out.println(order);
        em.clear();

        order = (Order) session
                .createCriteria(Order.class)
                .add(Restrictions.idEq(order.getOrderId()))
                .setFetchMode("orderLines", FetchMode.JOIN)
                .uniqueResult();

        em.clear();

        System.out.println(order);
        System.out.println(order.getOrderLines().size());

        order = (Order) session.createQuery("from Order o left join fetch o.orderLines where id = :id")
                .setParameter("id", order.getOrderId()).uniqueResult();

        em.clear();

        System.out.println(order);
        System.out.println(order.getOrderLines().size());
    }

    @Test(expected = Exception.class)
    public void insert_withoutExistingCustomer() throws Exception {
        Order order = createOrder();
        repo.insert(order);
    }

    @Test
    public void getOrder() throws Exception {
        Order order = createOrder();
        em.persist(order.getCustomer());
        em.persist(order);
        em.clear();

        Order foundOrder = repo.getOrder(order.getOrderId());
        assertThat(foundOrder).isEqualTo(order);
        assertThat(foundOrder.getCustomer()).isEqualTo(this.createDummyCustomer());
    }
}
