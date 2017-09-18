package pl.michal.olszewski.inventoryservice.inventory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.michal.olszewski.inventoryservice.product.Product;
import pl.michal.olszewski.inventoryservice.product.ProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public Product getProduct(String productName) {
        log.info("Pobieram produkt o nazwie {}", productName);
        Optional<Product> byName = productRepository.findByName(productName);
        byName.ifPresent(v -> {
            List<Inventory> byProduct = inventoryRepository.findByProduct(v);
            v.setInStock(!byProduct.isEmpty());
            log.info("Produkt {} jest dostępny w magazynach więc zmieniam parametr ", v);
        });
        return byName.orElse(null);
    }

    List<Inventory> getAvailableInventoryForProducts(String productNames) {
        log.info("Pobieram Inventory dla produktów o nazwie {}", productNames);
        return inventoryRepository.findByProductNames(Arrays.asList(productNames.split(",")));
    }
}
