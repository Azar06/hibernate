package com.valtech.tp.app1.domain.model.shippingarea;

import com.valtech.tp.app1.domain.model.address.Address;
import com.valtech.tp.app1.domain.model.commun.DomainEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ShippingArea extends DomainEntity {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String companyName;

    @Embedded
    private Address address;

    public ShippingArea() {
    }

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    @Override
    public Object getNaturalId() {
        return address;
    }
}
