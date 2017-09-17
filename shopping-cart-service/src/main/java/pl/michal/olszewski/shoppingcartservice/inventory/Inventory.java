package pl.michal.olszewski.shoppingcartservice.inventory;

import lombok.Data;
import pl.michal.olszewski.shoppingcartservice.product.Product;
import pl.michal.olszewski.shoppingcartservice.warehouse.Warehouse;

import java.io.Serializable;

@Data
public class Inventory implements Serializable {

    private Long id;
    private String inventoryNumber;
    private Product product;
    private Warehouse warehouse;
    private Long inventoryStatus;
}
