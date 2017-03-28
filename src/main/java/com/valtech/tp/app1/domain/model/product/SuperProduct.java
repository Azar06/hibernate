package com.valtech.tp.app1.domain.model.product;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("super")
public class SuperProduct extends Product {

    private String superInformation;

    public SuperProduct() {
    }

    public SuperProduct(Long id) {
        super(id);
    }

    public SuperProduct(String reference) {
        super(reference);
    }

    public String getSuperInformation() {
        return superInformation;
    }

    public void setSuperInformation(String superInformation) {
        this.superInformation = superInformation;
    }
}
