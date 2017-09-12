package pl.michal.olszewski.inventoryservice.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michal.olszewski.inventoryservice.catalog.Catalog;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;
    private String description;
    private BigDecimal unitPrice;

    @Transient
    private Boolean inStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalogId")
    private Catalog catalog;

    public Product(String name, String description, BigDecimal unitPrice) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
    }
}
