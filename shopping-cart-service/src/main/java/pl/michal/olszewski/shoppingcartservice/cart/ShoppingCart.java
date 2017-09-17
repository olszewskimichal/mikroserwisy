package pl.michal.olszewski.shoppingcartservice.cart;

import lombok.Data;
import pl.michal.olszewski.shoppingcartservice.catalog.Catalog;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class ShoppingCart {
    //Mapa id produktów i ich ilości
    private Map<Long, Integer> productMap = new HashMap<>();
    private List<LineItem> lineItems = new ArrayList<>();
    private Catalog catalog;

    public ShoppingCart(Catalog catalog) {
        this.catalog = catalog;
    }

    public List<LineItem> getLineItems() {
        lineItems = productMap.entrySet()
                .stream()
                .map(item -> new LineItem(item.getKey(), catalog.getProducts().stream()
                        .filter(prd -> Objects.equals(prd.getId(), item.getKey()))
                        .findFirst()
                        .orElse(null), item.getValue()))
                .filter(item -> item.getQuantity() > 0)
                .collect(Collectors.toList());

        if (lineItems.stream().anyMatch(item -> item.getProduct() == null)) {
            throw new IllegalArgumentException("Product not found in catalog");
        }

        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public ShoppingCart incorporate(CartEvent cartEvent) {
        List<CartEventType> validCartEventTypes = Arrays.asList(CartEventType.ADD_ITEM, CartEventType.REMOVE_ITEM);

        // The CartEvent's type must be either ADD_ITEM or REMOVE_ITEM
        CartEventType eventType = CartEventType.fromValue(cartEvent.getCartEventType());
        if (validCartEventTypes.contains(eventType)){
            // Update the aggregate view of each line item's quantity from the event type
            productMap.put(cartEvent.getProductId(), productMap.getOrDefault(cartEvent.getProductId(), 0) + (cartEvent.getQuantity() * (cartEvent.getCartEventType().equals(CartEventType.ADD_ITEM.getValue()) ? 1 : -1)));
        }

        return this;
    }

    public static Boolean isTerminal(CartEventType eventType) {
        return (eventType == CartEventType.CLEAR_CART || eventType == CartEventType.CHECKOUT);
    }
}
