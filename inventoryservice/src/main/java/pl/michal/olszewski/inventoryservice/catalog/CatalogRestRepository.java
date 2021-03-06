package pl.michal.olszewski.inventoryservice.catalog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.michal.olszewski.inventoryservice.product.Product;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/catalogs")
@Slf4j
public class CatalogRestRepository {

    private final CatalogService catalogService;

    public CatalogRestRepository(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping(path = "/{catalogId}/products")
    public ResponseEntity<Set<Product>> getProductsForCatalog(@PathVariable("catalogId") Long catalogId) {
        log.info("Pobieram katalog po id {}", catalogId);
        return Optional.ofNullable(catalogService.getProductsForCatalog(catalogId))
                .map(result -> {
                    log.debug("Pobrałem produkty {}", result);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/search/findCatalogByCatalogNumber")
    public ResponseEntity getProducstFromCatalogNumber(@RequestParam("catalogNumber") Long catalogNumber) {
        return Optional.ofNullable(catalogService.getCatalogByCatalogNumber(catalogNumber))
                .map(result -> {
                    log.debug("Pobrałem kategorie {}", result);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
