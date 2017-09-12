package pl.michal.olszewski.inventoryservice.catalog;

import org.springframework.stereotype.Service;
import pl.michal.olszewski.inventoryservice.product.Product;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public Catalog getCatalogByCatalogNumber(Long catalogName) {
        Optional<Catalog> catalog = catalogRepository.findByCatalogNumber(catalogName);
        return catalog.orElse(null);
    }

    public Set<Product> getProductsForCatalog(Long catalogId){
        return Optional.ofNullable(catalogRepository.findOne(catalogId)).map(v -> v.getProducts()).orElse(new HashSet<>());
    }
}
