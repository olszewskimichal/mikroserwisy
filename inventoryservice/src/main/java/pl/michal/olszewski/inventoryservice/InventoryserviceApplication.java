package pl.michal.olszewski.inventoryservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.michal.olszewski.inventoryservice.catalog.Catalog;
import pl.michal.olszewski.inventoryservice.catalog.CatalogRepository;
import pl.michal.olszewski.inventoryservice.inventory.Inventory;
import pl.michal.olszewski.inventoryservice.inventory.InventoryRepository;
import pl.michal.olszewski.inventoryservice.product.Product;
import pl.michal.olszewski.inventoryservice.product.ProductRepository;

import java.math.BigDecimal;

@SpringBootApplication
@EnableTransactionManagement
@EnableEurekaClient
@EnableHystrix
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

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return new MappingJackson2HttpMessageConverter(mapper);
    }

    public static void main(String[] args) {
        SpringApplication.run(InventoryserviceApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        Product product = new Product("test", "opis", BigDecimal.TEN);
        product.setInStock(true);
        Catalog catalog = new Catalog(997L, "policyjny");
        catalog.addProduct(product);
        catalogRepository.save(catalog);

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setInventoryNumber("test");
        inventoryRepository.save(inventory);
    }
}
