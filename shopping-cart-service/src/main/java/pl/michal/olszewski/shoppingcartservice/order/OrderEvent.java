package pl.michal.olszewski.shoppingcartservice.order;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderEvent implements Serializable {
    private Long id;
    private OrderEventType type;
    private Long orderId;

    public OrderEvent(OrderEventType type, Long orderId) {
        this.type = type;
        this.orderId = orderId;
    }
}
