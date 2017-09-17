package pl.michal.olszewski.orderservice.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Long productId;
    private Integer quantity;
    private BigDecimal price, tax;

    public OrderLineItem(String name, Long productId, Integer quantity, BigDecimal price, BigDecimal tax) {
        this.name = name;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.tax = tax;
    }
}
