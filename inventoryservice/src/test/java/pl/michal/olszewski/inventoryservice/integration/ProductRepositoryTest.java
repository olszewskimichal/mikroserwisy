package pl.michal.olszewski.inventoryservice.integration;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.inventoryservice.product.Product;
import pl.michal.olszewski.inventoryservice.product.ProductRepository;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryTest extends IntegrationTestBase {

    @Autowired
    private ProductRepository repository;

    @Test
    public void shouldFindProductByName() {
        Product product = new Product("productName","", BigDecimal.TEN);
        entityManager.persistAndFlush(product);

        assertThat(repository.findByName("productName").isPresent()).isTrue();
    }
    @Test
    public void shouldNotFindProductByNameWhereNotExists() {
        assertThat(repository.findByName("ProductName2").isPresent()).isFalse();
    }
    
}
