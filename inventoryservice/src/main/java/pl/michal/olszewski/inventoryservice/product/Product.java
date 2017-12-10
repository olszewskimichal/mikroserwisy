package pl.michal.olszewski.inventoryservice.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.michal.olszewski.inventoryservice.catalog.Catalog;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private Boolean inStock;

    public Product(String name, String description, BigDecimal unitPrice) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.inStock = true;
    }
}
