package com.valtech.tp.app1.domain.model.address;

import com.sun.istack.internal.NotNull;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Embeddable
public class Address {
    @NotBlank
    @Size(max=128)
    private String street;
    @Size(max=128)
    private String street2;
    @NotBlank
    @Size(max=10)
    private String postCode;
    @NotBlank
    @Size(max=50)
    private String city;
    @NotBlank
    @Size(max=50)
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (street2 != null ? !street2.equals(address.street2) : address.street2 != null) return false;
        if (postCode != null ? !postCode.equals(address.postCode) : address.postCode != null) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        return country != null ? country.equals(address.country) : address.country == null;
    }

    @Override
    public int hashCode() {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + (street2 != null ? street2.hashCode() : 0);
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
