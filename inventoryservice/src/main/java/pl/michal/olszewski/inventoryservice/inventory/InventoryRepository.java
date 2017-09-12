package pl.michal.olszewski.inventoryservice.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.michal.olszewski.inventoryservice.product.Product;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByProduct(Product product);

    @Query(value = "select i from Inventory i where i.product.name in ?1")
    List<Inventory> findByProductNames(List<String> productNames);
}
