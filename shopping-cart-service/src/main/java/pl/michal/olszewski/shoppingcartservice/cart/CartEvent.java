package pl.michal.olszewski.shoppingcartservice.cart;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Data
@Table(name = "cart_event", indexes = {@Index(name = "IDX_CART_EVENT_USER", columnList = "id,userId")})
@Entity
@Slf4j
public class CartEvent {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private CartEventType cartEventType;
    private Long userId;
    private Long productId;
    private Integer quantity;

    public CartEvent() {
    }

    public CartEvent(CartEventType cartEventType, Long userId) {
        this.cartEventType = cartEventType;
        this.userId = userId;
    }

    public CartEvent(CartEventType cartEventType, Long userId, Long productId, Integer quantity) {
        this.cartEventType = cartEventType;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
