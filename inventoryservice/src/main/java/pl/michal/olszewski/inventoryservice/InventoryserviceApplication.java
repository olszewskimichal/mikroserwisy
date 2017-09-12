package pl.michal.olszewski.inventoryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.michal.olszewski.inventoryservice.catalog.Catalog;
import pl.michal.olszewski.inventoryservice.catalog.CatalogRepository;
import pl.michal.olszewski.inventoryservice.inventory.Inventory;
import pl.michal.olszewski.inventoryservice.inventory.InventoryRepository;
import pl.michal.olszewski.inventoryservice.product.Product;
import pl.michal.olszewski.inventoryservice.product.ProductRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class InventoryserviceApplication implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final CatalogRepository catalogRepository;

    @Autowired
    public InventoryserviceApplication(InventoryRepository inventoryRepository, ProductRepository productRepository, CatalogRepository catalogRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.catalogRepository = catalogRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(InventoryserviceApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        Product product = new Product("test", "", BigDecimal.TEN);
        Catalog catalog = new Catalog(997L, "policyjny");
        catalog.addProduct(product);
        catalogRepository.save(catalog);

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setInventoryNumber("test");
        inventoryRepository.save(inventory);
    }
}
