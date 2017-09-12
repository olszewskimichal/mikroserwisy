package pl.olszewski.michal.catalogservice.catalog;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class CatalogInfo implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private Long catalogId;
    private Boolean active;

    public CatalogInfo() {
        active = false;
    }

    public CatalogInfo(Long catalogId) {
        this();
        this.catalogId = catalogId;
    }

    public CatalogInfo(Long catalogId, Boolean active) {
        this(catalogId);
        this.active = active;
    }
}
