package pl.michal.olszewski.inventoryservice.integration;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.inventoryservice.catalog.Catalog;
import pl.michal.olszewski.inventoryservice.catalog.CatalogRepository;
import pl.michal.olszewski.inventoryservice.product.Product;
import pl.michal.olszewski.inventoryservice.product.ProductRepository;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CatalogRepositoryTest extends IntegrationTestBase {

    @Autowired
    private CatalogRepository repository;

    @Test
    public void shouldFindCatalogByCatalogNumber() {
        Catalog catalog = new Catalog();
        catalog.setCatalogNumber(3L);
        entityManager.persistAndFlush(catalog);

        assertThat(repository.findByCatalogNumber(3L).isPresent()).isTrue();
    }

    @Test
    public void shouldNotFindCatalogByCatalogNumberWhereNotExists() {
        assertThat(repository.findByCatalogNumber(4L).isPresent()).isFalse();
    }

}
