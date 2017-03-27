package com.valtech.tp.app1.domain.repository.review;

import com.valtech.tp.app1.domain.model.review.Review;
import com.valtech.tp.app1.domain.repository.commun.DomainRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepository extends DomainRepository {

    public void insert(Review review) {
        getEntityManager().persist(review);
    }
}
