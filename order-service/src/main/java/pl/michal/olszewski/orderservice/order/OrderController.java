package pl.michal.olszewski.orderservice.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/v1")
@Slf4j
@CrossOrigin
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/accounts/{userName}/orders")
    public ResponseEntity getOrders(@PathVariable("userName") String userName) {
        log.info("Pobieram zamowienia dla uzytkownika {}", userName);
        return Optional.ofNullable(orderService.getOrdersForUserName(userName))
                .map(a -> new ResponseEntity<>(a, OK))
                .orElseThrow(() -> new IllegalArgumentException("Accounts for user do not exist"));
    }

    @PostMapping(path = "/orders/{orderId}/events")
    public ResponseEntity addOrderEvent(@RequestBody OrderEvent orderEvent, @PathVariable("orderId") Long orderId) throws Exception {
        log.info("POST zdarzenia {} dla zamowienia o id {}", orderEvent, orderId);
        assert orderEvent != null;
        assert orderId != null;
        assert !Objects.equals(orderId, orderEvent.getOrderId());
        return Optional.ofNullable(orderService.addOrderEvent(orderEvent))
                .map(a -> new ResponseEntity<>(NO_CONTENT))
                .orElseThrow(() -> new IllegalArgumentException("Order event could not be applied to order"));
    }

    @RequestMapping(path = "/orders/{orderId}")
    public ResponseEntity getOrder(@PathVariable("orderId") Long orderId) throws Exception {
        log.info("Pobieram  zdarzenie o id {}", orderId);
        assert orderId != null;
        return Optional.ofNullable(orderService.getOrder(orderId))
                .map(a -> new ResponseEntity<>(a, OK))
                .orElseThrow(() -> new Exception("Could not retrieve order"));
    }

    @PostMapping(path = "/orders")
    public ResponseEntity createOrder(@RequestBody List<OrderLineItem> lineItems) throws Exception {
        log.info("POST zamowienia dla list itemów {}", lineItems);
        assert lineItems != null;
        assert lineItems.size() > 0;
        return Optional.ofNullable(orderService.createOrder(lineItems))
                .map(a -> new ResponseEntity<>(a, OK))
                .orElseThrow(() -> new Exception("Could not create the order"));
    }
}
