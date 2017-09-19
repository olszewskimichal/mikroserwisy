package pl.olszewski.michal.catalogservice.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.olszewski.michal.catalogservice.catalog.Catalog;

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
