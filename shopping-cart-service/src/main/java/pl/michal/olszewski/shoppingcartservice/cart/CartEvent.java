package pl.michal.olszewski.shoppingcartservice.cart;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "cart_event", indexes = {@Index(name = "IDX_CART_EVENT_USER", columnList = "id,userId")})
@Entity
public class CartEvent {
    @Id
    @GeneratedValue
    private Long id;
    private Long cartEventType;
    private Long userId;
    private Long productId;
    private Integer quantity;

    public CartEvent(CartEventType cartEventType, Long userId) {
        this.cartEventType = cartEventType.getValue();
        this.userId = userId;
    }
}
