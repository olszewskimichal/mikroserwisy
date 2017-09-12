package pl.michal.olszewski.inventoryservice.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.michal.olszewski.inventoryservice.inventory.Inventory;
import pl.michal.olszewski.inventoryservice.inventory.InventoryRepository;
import pl.michal.olszewski.inventoryservice.inventory.InventoryService;
import pl.michal.olszewski.inventoryservice.product.Product;
import pl.michal.olszewski.inventoryservice.product.ProductRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class InventoryServiceTest {

    @Mock
    private InventoryService service;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new InventoryService(inventoryRepository, productRepository);
    }

    @Test
    public void shouldSetInStockWhenProductIsInInventory() {
        Product product = new Product("productName", "", BigDecimal.TEN);
        given(productRepository.findByName("productName")).willReturn(Optional.of(product));
        given(inventoryRepository.findByProduct(product)).willReturn(Arrays.asList(new Inventory()));

        Product productFromService = service.getProduct("productName");
        assertThat(productFromService.getInStock()).isTrue();
    }
}
