package pl.michal.olszewski.shoppingcartservice.cart;

import lombok.Data;
import pl.michal.olszewski.shoppingcartservice.order.Order;

import java.io.Serializable;

@Data
public class CheckoutResult implements Serializable {
    private String resultMessage;
    private Order order;
}
