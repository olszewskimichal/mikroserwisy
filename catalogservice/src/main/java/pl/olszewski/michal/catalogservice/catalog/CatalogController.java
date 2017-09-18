package pl.olszewski.michal.catalogservice.catalog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.olszewski.michal.catalogservice.product.Product;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/catalog")
@Slf4j
@CrossOrigin
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping()
    public ResponseEntity<Catalog> getCatalog() {
        log.info("Próba pobrania przez API aktywnego katalogu ");
        return Optional.ofNullable(catalogService.getCatalog())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/products/{productName}")
    public ResponseEntity<Product> getProduct(@PathVariable("productName") String productName) {
        log.info("Próba pobrania przez API produktu o nazwie {}", productName);
        return Optional.ofNullable(catalogService.getProduct(productName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
