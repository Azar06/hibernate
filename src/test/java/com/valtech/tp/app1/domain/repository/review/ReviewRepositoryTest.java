package com.valtech.tp.app1.domain.repository.review;

import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.tp.app1.domain.model.product.Product;
import com.valtech.tp.app1.domain.model.review.Review;
import com.valtech.tp.app1.domain.repository.customer.CustomerRepositoryTest;
import com.valtech.tp.app1.domain.repository.product.ProductRepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReviewRepositoryTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private ReviewRepository repo;


    public static Review createDummyReview(Customer customer, Product product) {
        Review review = new Review(customer, product);
        review.setStars(4);
        review.setReview("Very good");
        return review;
    }

    @Test
    public void insert() throws Exception {
        Customer customer = CustomerRepositoryTest.createDummyCustomer();
        Product product = ProductRepositoryTest.createDummyProduct();
        em.persist(customer);
        em.persist(product);

        Review review = createDummyReview(customer, product);
        repo.insert(review);
        em.flush();
        em.clear();

        Review loadedReview = em.find(Review.class, review.getId());
        assertThat(loadedReview).isNotNull();
        assertThat(loadedReview).isEqualTo(review);

    }
}
