package pl.michal.olszewski.shoppingcartservice.order;

import lombok.Data;
import pl.michal.olszewski.shoppingcartservice.address.Address;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Order implements Serializable {
    private Long orderId;
    private String accountNumber;
    private OrderStatus orderStatus;
    private List<OrderLineItem> lineItems = new ArrayList<>();
    private Address shippingAddress;
}
