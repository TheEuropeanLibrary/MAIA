package org.theeuropeanlibrary.maia.tel.model.provider.definitions;

import java.util.Objects;
import org.theeuropeanlibrary.maia.common.FieldId;

/**
 * Address object
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28.11.2014
 */
public class Address {

    @FieldId(1)
    private String street;

    @FieldId(2)
    private String postcode;

    @FieldId(3)
    private String city;
    
    public Address() {
        // default constructor
    }

    public Address(String street, String postcode, String city) {
        this.street = street;
        this.postcode = postcode;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.postcode, other.postcode)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Address{" + "street=" + street + ", postcode=" + postcode + ", city=" + city + '}';
    }
}
