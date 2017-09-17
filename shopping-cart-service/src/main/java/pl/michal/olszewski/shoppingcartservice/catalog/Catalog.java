package pl.michal.olszewski.shoppingcartservice.catalog;

import lombok.Data;
import pl.michal.olszewski.shoppingcartservice.product.Product;

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
        product.setCatalog(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.setCatalog(null);
    }
}
