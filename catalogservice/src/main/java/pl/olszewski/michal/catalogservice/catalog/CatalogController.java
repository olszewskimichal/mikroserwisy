package pl.olszewski.michal.catalogservice.catalog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.olszewski.michal.catalogservice.product.Product;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/catalog")
@CrossOrigin
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping()
    public ResponseEntity<Catalog> getCatalog() {
        return Optional.ofNullable(catalogService.getCatalog())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/products/{productName}")
    public ResponseEntity<Product> getProduct(@PathVariable("productName") String productName) {
        return Optional.ofNullable(catalogService.getProduct(productName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
