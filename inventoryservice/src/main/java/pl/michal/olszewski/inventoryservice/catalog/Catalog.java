package pl.michal.olszewski.inventoryservice.catalog;

import lombok.Data;
import pl.michal.olszewski.inventoryservice.product.Product;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Catalog {
    @Id
    @GeneratedValue
    private Long id;
    private Long catalogNumber;
    private String name;

    @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> products = new HashSet<>();

    public void addProduct(Product product) {
        products.add(product);
        product.setCatalog(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.setCatalog(null);
    }
}