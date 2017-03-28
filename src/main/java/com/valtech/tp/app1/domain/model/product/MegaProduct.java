package com.valtech.tp.app1.domain.model.product;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("mega")
public class MegaProduct extends Product {

    public MegaProduct() {
    }

    public MegaProduct(Long id) {
        super(id);
    }

    public MegaProduct(String reference) {
        super(reference);
    }
}
