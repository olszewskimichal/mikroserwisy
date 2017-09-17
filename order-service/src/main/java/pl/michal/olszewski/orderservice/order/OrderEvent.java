package pl.michal.olszewski.orderservice.order;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class OrderEvent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private OrderEventType type;
    private Long orderId;

    public OrderEvent() {
    }

    public OrderEvent(OrderEventType type) {
        this();
        this.type = type;
    }

    public OrderEvent(OrderEventType type, Long orderId) {
        this.type = type;
        this.orderId = orderId;
    }
}
