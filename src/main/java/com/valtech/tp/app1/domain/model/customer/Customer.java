package com.valtech.tp.app1.domain.model.customer;

import com.google.common.annotations.VisibleForTesting;
import com.valtech.tp.app1.domain.model.address.Address;
import com.valtech.tp.app1.domain.model.review.Review;
import com.valtech.utils.model.EntityObject;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Customer extends EntityObject {
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Email
    @Size(max=64)
    private String email;

    @NotBlank
    @Size(max=32)
    private String lastName;

    @NotBlank
    @Size(max=32)
    private String firstName;

    @Embedded
    private Address address;

    /*
    @OneToMany
    private List<Review> reviews;
    */

    @VisibleForTesting
    private Customer() {
    }

    public Customer(String email) {
        this.email = email;
    }

    public Customer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public Object getNaturalId() {
        return email;
    }

}
