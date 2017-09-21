package pl.michal.olszewski.shoppingcartservice.cart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Slf4j
public class ShoppingCartService {
    private static final BigDecimal TAX = BigDecimal.valueOf(0.6);
    private final CartEventRepository cartEventRepository;
    private final RestTemplate restTemplate;

    public ShoppingCartService(CartEventRepository cartEventRepository, RestTemplate restTemplate) {
        this.cartEventRepository = cartEventRepository;
        this.restTemplate = restTemplate;
    }

    private User getAuthenticatedUser() {
        log.info("Pobieram przez API zalogowanego uzytkownika");
        return restTemplate.getForObject("http://localhost:8080/api/v1/users/user/test", User.class);
    }

    @Transactional
    public Boolean addCartEvent(CartEvent cartEvent) {
        User user = getAuthenticatedUser();
        if (user != null) {
            log.info("Zapis cartEventu do bazy dla uzytkownika {}", user);
            cartEvent.setUserId(user.getId());
            cartEventRepository.save(cartEvent);
            log.debug("Dodano cartEvent i zapisano w bazie {}", cartEvent);
        } else {
            log.error("Z jakiegos powodu nie pobralem uzytkownika");
            return null;
        }
        return true;
    }

    private void addCartEvent(CartEvent cartEvent, User user) {
        log.debug("dodanie cartEventu {} dla uzytkownika {} i zapis do bazy", cartEvent, user);
        if (user != null) {
            cartEvent.setUserId(user.getId());
            cartEventRepository.save(cartEvent);
        }
    }

    @Transactional
    public ShoppingCart getShoppingCart() {
        log.info("Pobieram koszyk uzytkownika");
        User user = getAuthenticatedUser();
        ShoppingCart shoppingCart = null;
        if (user != null) {
            Catalog catalog = restTemplate.getForObject("http://localhost:8083/api/v1/catalog", Catalog.class);
            log.debug("Pobrałem katalog {} przez REST i teraz bede agregowac eventy", catalog);
            shoppingCart = aggregateCartEvents(user, catalog);
        }
        return shoppingCart;
    }

    private ShoppingCart aggregateCartEvents(User user, Catalog catalog) {
        log.info("Agregacja eventów uzytkownika {} i katalogu {}", user, catalog);
        List<CartEvent> cartEvents = cartEventRepository.findByUserId(user.getId());


        ShoppingCart shoppingCart = new ShoppingCart(catalog);
        // Aggregate the state of the shopping cart
        cartEvents.stream()
                .filter(cartEvent -> !ShoppingCart.isTerminal(cartEvent.getCartEventType())).forEach(shoppingCart::incorporate);

        shoppingCart.convertLineItems();
        return shoppingCart;
    }

    CheckoutResult checkout() throws Exception {
        log.info("Checkout");
        CheckoutResult checkoutResult = new CheckoutResult();

        // Check available inventory
        ShoppingCart currentCart = getShoppingCart();


        if (currentCart != null) {
            currentCart.convertLineItems();
            // Reconcile the current cart with the available inventory
            Inventory[] inventory = restTemplate.getForObject(String.format("http://localhost:8082/api/v1/inventory?productNames=%s", currentCart.getLineItems()
                    .stream()
                    .map(v -> v.getProduct().getName()).map(Object::toString)
                    .collect(Collectors.joining(","))), Inventory[].class);
            log.debug("Pobralem przez api wszystkie Inventory {} dla produktów w koszyku", Arrays.toString(inventory));

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
                    log.debug("Po wysłaniu POSTem zamowienia wyglada następujaco {}", orderResponse);

                    if (orderResponse != null) {
                        // Order creation successful
                        checkoutResult.setResultMessage("Order created");

                        // Add order event
                        log.info("Zamowienie stworzone {}",orderResponse);
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

    private Boolean checkAvailableInventory(CheckoutResult checkoutResult, ShoppingCart currentCart, Map<Long, Long> inventoryItems) {
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
            log.info("Insufficient inventory available for {}", productIdList);
            hasInventory = false;
        }

        return hasInventory;
    }
}
