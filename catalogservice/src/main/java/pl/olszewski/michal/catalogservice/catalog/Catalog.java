package pl.olszewski.michal.catalogservice.catalog;

import lombok.Data;
import pl.olszewski.michal.catalogservice.product.Product;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class Catalog implements Serializable {
    private Long id;
    private Long catalogNumber;
    private String name;
    private Set<Product> products = new HashSet<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }
}
