package pl.michal.olszewski.inventoryservice.integration;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.inventoryservice.inventory.Inventory;
import pl.michal.olszewski.inventoryservice.inventory.InventoryRepository;
import pl.michal.olszewski.inventoryservice.product.Product;
import pl.michal.olszewski.inventoryservice.product.ProductRepository;

import java.math.BigDecimal;
import java.util.Arrays;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryRepositoryTest extends IntegrationTestBase {

    @Autowired
    private InventoryRepository repository;

    @Test
    public void shouldFindInventoryByProduct() {
        Product product = new Product("productName2", "", BigDecimal.TEN);
        entityManager.persistAndFlush(product);
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        entityManager.persistAndFlush(inventory);

        assertThat(repository.findByProduct(product)).isNotEmpty();
        assertThat(repository.findByProduct(product).size()).isEqualTo(1);
    }

    @Test
    public void shouldFindInventoriesByProductList() {
        Product product = new Product("productName3", "", BigDecimal.TEN);
        entityManager.persistAndFlush(product);
        Product product2 = new Product("productName4", "", BigDecimal.TEN);
        entityManager.persistAndFlush(product2);
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        entityManager.persistAndFlush(inventory);
        Inventory inventory2 = new Inventory();
        inventory2.setProduct(product2);
        entityManager.persistAndFlush(inventory2);

        assertThat(repository.findByProductNames(asList("productName3","productName4"))).isNotEmpty();
        assertThat(repository.findByProductNames(asList("productName3","productName4")).size()).isEqualTo(2);
    }

}
