package pl.michal.olszewski.orderservice.order;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.michal.olszewski.orderservice.address.Address;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Order implements Serializable {
    @Id
    @GeneratedValue
    private Long orderId;
    private String userName;
    @Transient
    private OrderStatus orderStatus;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderLineItem> lineItems = new HashSet<>();
    @Embedded
    private Address shippingAddress;

    @CreatedDate
    private Long createdAt;

    @LastModifiedDate
    private Long lastModified;

    public Order() {
        this.orderStatus = OrderStatus.PURCHASED;
    }

    public Order(String userName, Address shippingAddress) {
        this();
        this.userName = userName;
        this.shippingAddress = shippingAddress;
    }

    /**
     * The incorporate method uses a simple state machine for an order's status to generate
     * the current state of an order using event sourcing and aggregation.
     * <p>
     * The event diagram below represents how events are incorporated into generating the
     * order status. The event log for the order status can be used to rollback the state
     * of the order in the case of a failed distributed transaction.
     * <p>
     * Events:   +<--PURCHASED+  +<--CREATED+  +<---ORDERED+  +<----SHIPPED+
     * *         |            |  |          |  |           |  |            |
     * Status    +PURCHASED---+PENDING------+CONFIRMED-----+SHIPPED--------+DELIVERED
     * *         |            |  |          |  |           |  |            |
     * Events:   +CREATED---->+  +ORDERED-->+  +SHIPPED--->+  +DELIVERED-->+
     *
     * @param orderEvent is the event to incorporate into the state machine
     * @return the aggregate {@link Order} with the aggregated order status
     */
    public Order incorporate(OrderEvent orderEvent) {
        log.info("Incorporate dla eventu {} prze operacja status to {}", orderEvent, orderStatus);
        if (orderStatus == null)
            orderStatus = OrderStatus.PURCHASED;

        switch (orderStatus) {
            case PURCHASED:
                if (orderEvent.getType() == OrderEventType.CREATED)
                    orderStatus = OrderStatus.PENDING;
                break;
            case PENDING:
                if (orderEvent.getType() == OrderEventType.ORDERED) {
                    orderStatus = OrderStatus.CONFIRMED;
                } else if (orderEvent.getType() == OrderEventType.PURCHASED) {
                    orderStatus = OrderStatus.PURCHASED;
                }
                break;
            case CONFIRMED:
                if (orderEvent.getType() == OrderEventType.SHIPPED) {
                    orderStatus = OrderStatus.SHIPPED;
                } else if (orderEvent.getType() == OrderEventType.CREATED) {
                    orderStatus = OrderStatus.PENDING;
                }
                break;
            case SHIPPED:
                if (orderEvent.getType() == OrderEventType.DELIVERED) {
                    orderStatus = OrderStatus.DELIVERED;
                } else if (orderEvent.getType() == OrderEventType.ORDERED) {
                    orderStatus = OrderStatus.CONFIRMED;
                }
                break;
            case DELIVERED:
                if (orderEvent.getType() == OrderEventType.SHIPPED)
                    orderStatus = OrderStatus.SHIPPED;
                break;
            default:
                // Invalid event type with regards to the order status
                break;
        }
        log.info("Po incorporate status to {}", orderStatus);

        return this;
    }
}
