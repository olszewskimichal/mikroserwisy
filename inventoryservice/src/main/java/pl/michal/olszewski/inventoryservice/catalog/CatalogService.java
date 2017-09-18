package pl.michal.olszewski.inventoryservice.catalog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.michal.olszewski.inventoryservice.product.Product;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public Catalog getCatalogByCatalogNumber(Long catalogName) {
        log.info("Pobieram katalog po nazwie {}", catalogName);
        Optional<Catalog> catalog = catalogRepository.findByCatalogNumber(catalogName);
        return catalog.orElse(null);
    }

    public Set<Product> getProductsForCatalog(Long catalogId) {
        log.info("Pobieram katalog po id {}", catalogId);
        return Optional.ofNullable(catalogRepository.findOne(catalogId)).map(Catalog::getProducts).orElse(new HashSet<>());
    }
}
