package pl.michal.olszewski.shoppingcartservice.warehouse;

import lombok.Data;
import pl.michal.olszewski.shoppingcartservice.address.Address;

import java.io.Serializable;

@Data
public class Warehouse implements Serializable {
    private Long id;
    private String name;
    private Address address;
}
