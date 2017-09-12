package pl.michal.olszewski.inventoryservice.inventory;

import lombok.Data;
import pl.michal.olszewski.inventoryservice.product.Product;
import pl.michal.olszewski.inventoryservice.warehouse.Warehouse;

import javax.persistence.*;

@Entity
@Data
public class Inventory {

    @Id
    @GeneratedValue
    private Long id;
    private String inventoryNumber;
    @OneToOne(fetch = FetchType.LAZY)
    private Product product;
    @OneToOne(fetch = FetchType.LAZY)
    private Warehouse warehouse;
    private Long inventoryStatus;
}
