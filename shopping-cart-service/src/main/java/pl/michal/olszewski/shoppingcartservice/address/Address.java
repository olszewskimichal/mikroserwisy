package pl.michal.olszewski.shoppingcartservice.address;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
public class Address implements Serializable {
    private String street;
    private String state;
    private String city;
    private String country;
    private String zipCode;
}
