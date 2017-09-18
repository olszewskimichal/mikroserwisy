package pl.olszewski.michal.catalogservice.catalog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.olszewski.michal.catalogservice.product.Product;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class CatalogService {
    private final CatalogInfoRepository catalogInfoRepository;
    private final RestTemplate restTemplate;

    public CatalogService(CatalogInfoRepository catalogInfoRepository, RestTemplate restTemplate) {
        this.catalogInfoRepository = catalogInfoRepository;
        this.restTemplate = restTemplate;
    }

    public Product getProduct(String productName) {
        log.info("pobieram produkt o nazwie {}", productName);
        return restTemplate.getForObject(String.format("http://localhost:8082/api/v1/products/%s",
                productName), Product.class);
    }

    private Catalog getCatalogFromAPI(Long catalogId) {
        log.info("pobieram katalog o id {}", catalogId);
        return restTemplate.getForObject(String.format("http://localhost:8082/api/v1/catalogs/search/findCatalogByCatalogNumber?catalogNumber=%s",
                catalogId), Catalog.class);
    }

    private List<Product> getProductsFromAPI(Catalog catalog) {
        log.info("pobieram produkty z danego katalogu {}", catalog);
        return Arrays.asList(restTemplate.getForObject(String.format("http://localhost:8082/api/v1/catalogs/%s/products",
                catalog.getId()), Product[].class));
    }

    public Catalog getCatalog() {
        log.info("Pobieram aktywny katalog");
        CatalogInfo activeCatalog = catalogInfoRepository.findByActive(true);
        if (activeCatalog == null) throw new IllegalArgumentException("Nie moze byc zadnego aktywnego katalogu");
        Catalog catalog = getCatalogFromAPI(activeCatalog.getCatalogId());
        catalog.setProducts(new HashSet<>(getProductsFromAPI(catalog)));
        log.info("Pobrany katalog to {}", catalog);
        return catalog;
    }
}
