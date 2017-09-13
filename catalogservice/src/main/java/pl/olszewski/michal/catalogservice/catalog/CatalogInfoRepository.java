package pl.olszewski.michal.catalogservice.catalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogInfoRepository extends JpaRepository<CatalogInfo, Long> {
    CatalogInfo findByActive(Boolean active);
}
