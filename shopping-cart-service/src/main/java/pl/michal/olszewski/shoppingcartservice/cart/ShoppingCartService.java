package pl.michal.olszewski.shoppingcartservice.cart;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.michal.olszewski.shoppingcartservice.catalog.Catalog;
import pl.michal.olszewski.shoppingcartservice.inventory.Inventory;
import pl.michal.olszewski.shoppingcartservice.order.Order;
import pl.michal.olszewski.shoppingcartservice.order.OrderEvent;
import pl.michal.olszewski.shoppingcartservice.order.OrderEventType;
import pl.michal.olszewski.shoppingcartservice.order.OrderLineItem;
import pl.michal.olszewski.shoppingcartservice.user.User;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
public class ShoppingCartService {
    public static final BigDecimal TAX = BigDecimal.valueOf(0.6);
    private final CartEventRepository cartEventRepository;
    private final RestTemplate restTemplate;

    public ShoppingCartService(CartEventRepository cartEventRepository, RestTemplate restTemplate) {
        this.cartEventRepository = cartEventRepository;
        this.restTemplate = restTemplate;
    }

    public User getAuthenticatedUser() {
        return restTemplate.getForObject("http://localhost:8080/api/v1/users/user/test", User.class);
    }

    public Boolean addCartEvent(CartEvent cartEvent) {
        User user = getAuthenticatedUser();
        if (user != null) {
            cartEvent.setUserId(user.getId());
            cartEventRepository.save(cartEvent);
        } else {
            return null;
        }
        return true;
    }

    public Boolean addCartEvent(CartEvent cartEvent, User user) {
        if (user != null) {
            cartEvent.setUserId(user.getId());
            cartEventRepository.save(cartEvent);
        } else {
            return null;
        }
        return true;
    }

    public ShoppingCart getShoppingCart() {
        User user = getAuthenticatedUser();
        ShoppingCart shoppingCart = null;
        if (user != null) {
            Catalog catalog = restTemplate.getForObject("http://localhost:8083/api/v1/catalog", Catalog.class);
            shoppingCart = aggregateCartEvents(user, catalog);
        }
        return shoppingCart;
    }

    public ShoppingCart aggregateCartEvents(User user, Catalog catalog) {
        Stream<CartEvent> cartEvents = cartEventRepository.findByUserId(user.getId());

        ShoppingCart shoppingCart = new ShoppingCart(catalog);
        // Aggregate the state of the shopping cart
        cartEvents.filter(cartEvent -> !ShoppingCart.isTerminal(CartEventType.fromValue(cartEvent.getCartEventType())))
                .forEach(shoppingCart::incorporate);

        shoppingCart.getLineItems();

        return shoppingCart;
    }

    public CheckoutResult checkout() throws Exception {
        CheckoutResult checkoutResult = new CheckoutResult();

        // Check available inventory
        ShoppingCart currentCart = getShoppingCart();


        if (currentCart != null) {
            // Reconcile the current cart with the available inventory
            Inventory[] inventory = restTemplate.getForObject(String.format("http://localhost:8082/api/v1/inventory?productIds=%s", currentCart.getLineItems()
                    .stream()
                    .map(LineItem::getProductId).map(Object::toString)
                    .collect(Collectors.joining(","))), Inventory[].class);

            if (inventory != null) {
                Map<Long, Long> inventoryItems = Arrays.stream(inventory)
                        .map(inv -> inv.getProduct().getId())
                        .collect(groupingBy(Function.identity(), counting()));

                if (checkAvailableInventory(checkoutResult, currentCart, inventoryItems)) {
                    // Reserve the available inventory

                    // Create a new order
                    Order orderResponse = restTemplate.postForObject("http://localhost:8086/api/v1/orders",
                            currentCart.getLineItems().stream()
                                    .map(prd ->
                                            new OrderLineItem(prd.getProduct().getName(), prd.getProductId(), prd.getQuantity(), prd.getProduct().getUnitPrice(), TAX))
                                    .collect(Collectors.toList()), Order.class);

                    if (orderResponse != null) {
                        // Order creation successful
                        checkoutResult.setResultMessage("Order created");

                        // Add order event
                        restTemplate.postForEntity(String.format("http://localhost:8086/api/v1/orders/%s/events", orderResponse.getOrderId()), new OrderEvent(OrderEventType.CREATED, orderResponse.getOrderId()), ResponseEntity.class);
                        checkoutResult.setOrder(orderResponse);
                    }

                    User user = getAuthenticatedUser();

                    // Clear the shopping cart
                    addCartEvent(new CartEvent(CartEventType.CHECKOUT, user.getId()), user);
                }
            }
        }

        // Return errors with available inventory
        return checkoutResult;
    }

    public Boolean checkAvailableInventory(CheckoutResult checkoutResult, ShoppingCart currentCart, Map<Long, Long> inventoryItems) {
        Boolean hasInventory = true;
        // Determine if inventory is available

        List<LineItem> inventoryAvailable = currentCart.getLineItems()
                .stream()
                .filter(item -> inventoryItems.get(item.getProductId()) - item.getQuantity() < 0)
                .collect(Collectors.toList());
        if (inventoryAvailable.size() > 0) {
            String productIdList = inventoryAvailable
                    .stream()
                    .map(LineItem::getProductId).map(Object::toString)
                    .collect(Collectors.joining(", "));
            checkoutResult.setResultMessage(String.format("Insufficient inventory available for %s. " + "Lower the quantity of these products and try again.", productIdList));
            hasInventory = false;
        }

        return hasInventory;
    }
}
