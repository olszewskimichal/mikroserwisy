package pl.michal.olszewski.shoppingcartservice.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michal.olszewski.shoppingcartservice.catalog.Catalog;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Product implements Serializable {
    private Long id;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private Boolean inStock;

    public Product(String name, String description, BigDecimal unitPrice) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
    }
}
