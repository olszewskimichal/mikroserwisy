package pl.michal.olszewski.shoppingcartservice.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderLineItem {
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
