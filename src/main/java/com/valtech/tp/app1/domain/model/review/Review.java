package com.valtech.tp.app1.domain.model.review;

import com.google.common.collect.ImmutableList;
import com.valtech.tp.app1.domain.model.commun.DomainEntity;
import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.tp.app1.domain.model.product.Product;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Review extends DomainEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @ManyToOne
    private Customer customer;

    @NaturalId
    @ManyToOne
    private Product product;

    @Range(min = 1, max = 5)
    @NotNull
    private Integer stars;

    @Size(max = 10000)
    private String review;

    private Review() {
    }

    public Review(Long id) {
        this.id = id;
    }

    public Review(Customer customer, Product product) {
        this.customer = customer;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public Object getNaturalId() {
        return ImmutableList.builder()
                .add(customer)
                .add(product)
                .build();
    }
}
