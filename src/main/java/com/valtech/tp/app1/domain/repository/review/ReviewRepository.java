package com.valtech.tp.app1.domain.repository.review;

import com.valtech.tp.app1.domain.model.review.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ReviewRepository {
    @Autowired
    private EntityManager em;

    public void insert(Review review) {
        em.persist(em);
    }
}
