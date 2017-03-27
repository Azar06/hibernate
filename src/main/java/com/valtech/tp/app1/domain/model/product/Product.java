package com.valtech.tp.app1.domain.model.product;

import com.valtech.tp.app1.domain.model.review.Review;
import com.valtech.util.model.EntityObject;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(
        name = "TAB_PRODUCT",
        indexes = {
                @Index(name = "I_Product_name", columnList = "name")
        }
)
public class Product extends EntityObject {
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column(name = "COL_REF")
    @Size(max=32)
    private String reference;

    @NotBlank
    @Size(max = 32)
    private String name;

    @Range(min = 0)
    private Double price;

    private String description;

    @OneToMany
    private List<Review> reviews;

    private Product() {
    }

    public Product(Long id) {
        this.id = id;
    }

    public Product(String reference) {
        this.reference = reference;
    }

    public Long getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public Object getNaturalId() {
        return reference;
    }
}
