package pl.michal.olszewski.orderservice.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/v1")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/accounts/{userName}/orders")
    public ResponseEntity getOrders(@PathVariable("userName") String userName){
        return Optional.ofNullable(orderService.getOrdersForAccount(userName))
                .map(a -> new ResponseEntity<>(a, OK))
                .orElseThrow(() -> new IllegalArgumentException("Accounts for user do not exist"));
    }

    @PostMapping(path = "/orders/{orderId}/events")
    public ResponseEntity addOrderEvent(@RequestBody OrderEvent orderEvent, @PathVariable("orderId") Long orderId) throws Exception {
        assert orderEvent != null;
        assert orderId != null;
        assert !Objects.equals(orderId, orderEvent.getOrderId());
        return Optional.ofNullable(orderService.addOrderEvent(orderEvent))
                .map(a -> new ResponseEntity<>(NO_CONTENT))
                .orElseThrow(() -> new IllegalArgumentException("Order event could not be applied to order"));
    }

    @RequestMapping(path = "/orders/{orderId}")
    public ResponseEntity getOrder(@PathVariable("orderId") Long orderId) throws Exception {
        assert orderId != null;
        return Optional.ofNullable(orderService.getOrder(orderId))
                .map(a -> new ResponseEntity<>(a, OK))
                .orElseThrow(() -> new Exception("Could not retrieve order"));
    }

    @PostMapping(path = "/orders")
    public ResponseEntity createOrder(@RequestBody List<OrderLineItem> lineItems) throws Exception {
        assert lineItems != null;
        assert lineItems.size() > 0;
        return Optional.ofNullable(orderService.createOrder(lineItems))
                .map(a -> new ResponseEntity<>(a, OK))
                .orElseThrow(() -> new Exception("Could not create the order"));
    }
}
