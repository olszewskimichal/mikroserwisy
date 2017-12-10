package pl.michal.olszewski.orderservice.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.michal.olszewski.orderservice.account.Account;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventRepository orderEventRepository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository, OrderEventRepository orderEventRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.orderEventRepository = orderEventRepository;
        this.restTemplate = restTemplate;
    }

    public Order createOrder(List<OrderLineItem> lineItems) {
        Account[] accounts = restTemplate.getForObject("http://localhost:8787/account-service/api/v1/accounts", Account[].class);

        Account defaultAccount = Arrays.stream(accounts)
                .findFirst()
                .orElse(null);
        if (defaultAccount == null) {
            return null;
        }
        Order newOrder = new Order(defaultAccount.getUserName(), defaultAccount.getShippingAddress());
        newOrder.setLineItems(new HashSet<>(lineItems));
        newOrder = orderRepository.save(newOrder);
        log.debug("Zamowienie po stworzeniu wyglada {}", newOrder);
        return newOrder;
    }

    public Boolean addOrderEvent(OrderEvent orderEvent) {
        log.info("Próba dodania zdarzenia dla zamowienia {}", orderEvent);
        Order order = orderRepository.findOne(orderEvent.getOrderId());
        orderEventRepository.save(orderEvent);
        return true;
    }

    public Order getOrder(Long orderId) {
        log.info("Pobieram zamowienie o id {}", orderId);
        // Get the order for the event
        Order order = orderRepository.findOne(orderId);

        List<OrderEvent> orderEvents = orderEventRepository.findByOrderId(order.getOrderId());
        orderEvents.stream()
                .filter(orderEvent -> orderEvent.getType() != OrderEventType.DELIVERED)
                .forEach(order::incorporate);
        return order;
    }

    public List<Order> getOrdersForUserName(String userName) {
        log.info("Pobieram zamowienia dla uzytkownika {}", userName);
        List<Order> orders = orderRepository.findByUserName(userName);

        return orders.stream()
                .map(order -> getOrder(order.getOrderId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
