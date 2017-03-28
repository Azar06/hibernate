package com.valtech.tp.app1.domain.model.product;

import com.valtech.tp.app1.domain.model.commun.DomainEntity;
import com.valtech.tp.app1.domain.model.review.Review;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 10)
@DiscriminatorValue("simple")
@Table(
        name = "Product_", // we can rename the table inside of the DataBase
        indexes = {
                @Index(name = "I_Product_name", columnList = "name") // we can create indexes to optimize requests
        }
)
public class Product extends DomainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
    @NotBlank
    @Column(name = "REF")
    @Size(max=32)
    private String reference;

    @NotBlank
    @Size(max = 32)
    private String name;

    @Range(min = 0)
    private Double price;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<Review> reviews;

    protected Product() {
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
