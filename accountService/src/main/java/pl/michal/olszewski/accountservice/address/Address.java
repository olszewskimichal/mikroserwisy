package pl.michal.olszewski.accountservice.address;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class Address {

    private String street;
    private String state;
    private String city;
    private String country;
    private String zipCode;

    public Address(String street, String state, String city, String country, String zipCode) {
        this.street = street;
        this.state = state;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
    }
}
