package pl.michal.olszewski.shoppingcartservice.cart;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pl.michal.olszewski.shoppingcartservice.catalog.Catalog;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Slf4j
public class ShoppingCart {
    //Mapa id produktów i ich ilości
    private Map<Long, Integer> productMap = new HashMap<>();
    private List<LineItem> lineItems;
    private Catalog catalog;

    public ShoppingCart(Catalog catalog) {
        this.catalog = catalog;
        this.lineItems = new ArrayList<>();
    }

    public List<LineItem> getLineItems() {
        if (lineItems == null)
            lineItems = new ArrayList<>();
        return lineItems;
    }

    void convertLineItems() {
        log.info("getLineItems dla listy przedmiotów {}", productMap);
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
        log.info("getLineItems po przefiltrowaniu wyszło {}", lineItems);
    }

    ShoppingCart incorporate(CartEvent cartEvent) {
        log.info("incorporate dla eventu {}", cartEvent);
        List<CartEventType> validCartEventTypes = Arrays.asList(CartEventType.ADD_ITEM, CartEventType.REMOVE_ITEM);

        // The CartEvent's type must be either ADD_ITEM or REMOVE_ITEM
        CartEventType eventType = cartEvent.getCartEventType();
        if (validCartEventTypes.contains(eventType)) {
            // Update the aggregate view of each line item's quantity from the event type
            productMap.put(cartEvent.getProductId(), productMap.getOrDefault(cartEvent.getProductId(), 0) + (cartEvent.getQuantity() * (cartEvent.getCartEventType().equals(CartEventType.ADD_ITEM) ? 1 : -1)));
        }

        return this;
    }

    static Boolean isTerminal(CartEventType eventType) {
        return (eventType == CartEventType.CLEAR_CART || eventType == CartEventType.CHECKOUT);
    }
}
