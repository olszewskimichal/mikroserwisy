package pl.michal.olszewski.inventoryservice.inventory;

import org.springframework.stereotype.Service;
import pl.michal.olszewski.inventoryservice.product.Product;
import pl.michal.olszewski.inventoryservice.product.ProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public Product getProduct(String productName) {
        Optional<Product> byName = productRepository.findByName(productName);
        byName.ifPresent(v -> {
            List<Inventory> byProduct = inventoryRepository.findByProduct(v);
            v.setInStock(!byProduct.isEmpty());
        });
        return byName.orElse(null);
    }

    List<Inventory> getAvailableInventoryForProducts(String productNames) {
        return inventoryRepository.findByProductNames(Arrays.asList(productNames.split(",")));
    }
}
