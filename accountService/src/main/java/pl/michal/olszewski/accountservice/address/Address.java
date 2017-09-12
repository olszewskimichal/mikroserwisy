package pl.michal.olszewski.accountservice.address;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Address {

    private String street;
    private String state;
    private String city;
    private String country;
    private String zipCode;
}
