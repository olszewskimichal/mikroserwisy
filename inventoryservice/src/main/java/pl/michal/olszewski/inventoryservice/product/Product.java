package pl.michal.olszewski.inventoryservice.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.michal.olszewski.inventoryservice.catalog.Catalog;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;
    private String description;
    private BigDecimal unitPrice;

    @Transient
    @JsonIgnore
    private Boolean inStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalogId")
    @JsonIgnore
    private Catalog catalog;

    public Product(String name, String description, BigDecimal unitPrice) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
    }
}
