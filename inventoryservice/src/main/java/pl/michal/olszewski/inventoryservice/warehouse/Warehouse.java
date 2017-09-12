package pl.michal.olszewski.inventoryservice.warehouse;

import lombok.Data;
import pl.michal.olszewski.inventoryservice.address.Address;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Warehouse {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Embedded
    private Address address;


}
