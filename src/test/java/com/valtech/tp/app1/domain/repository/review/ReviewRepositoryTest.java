package com.valtech.tp.app1.domain.repository.review;

import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.tp.app1.domain.model.product.Product;
import com.valtech.tp.app1.domain.model.review.Review;
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
    EntityManager em;
    @Autowired
    ReviewRepository repo;

    @Test
    public void insert_Test() throws Exception {
        Customer customer = new Customer("me@valtech.com");
        Product product = new Product("myRef");
        Review review = new Review(customer, product);

        repo.insert(review);
        em.flush();
        em.clear();

        Review loadedReview = em.find(Review.class, review.getNaturalId());
        assertThat(loadedReview).isNotNull();
        assertThat(loadedReview).isEqualTo(review);
    }
}
