package pl.michal.olszewski.inventoryservice.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.michal.olszewski.inventoryservice.product.Product;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class InventoryRestController {

    private final InventoryService service;

    public InventoryRestController(InventoryService service) {
        this.service = service;
    }

    @GetMapping(path = "/products/{productName}")
    public ResponseEntity<Product> getProduct(@PathVariable("productName") String productName) {
        return Optional.ofNullable(service.getProduct(productName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/inventory")
    public ResponseEntity getAvailableInventoryForProductNames(@RequestParam("productNames") String productNames) {
        return Optional.ofNullable(service.getAvailableInventoryForProducts(productNames))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
