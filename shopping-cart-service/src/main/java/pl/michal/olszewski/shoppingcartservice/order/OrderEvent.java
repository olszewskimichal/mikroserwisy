package pl.michal.olszewski.shoppingcartservice.order;

import java.io.Serializable;

public class OrderEvent implements Serializable {
    private Long id;
    private OrderEventType type;
    private Long orderId;

    public OrderEvent(OrderEventType type, Long orderId) {
        this.type = type;
        this.orderId = orderId;
    }
}
