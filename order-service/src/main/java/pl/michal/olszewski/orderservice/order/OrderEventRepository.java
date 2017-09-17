package pl.michal.olszewski.orderservice.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface OrderEventRepository extends JpaRepository<OrderEvent,Long> {
    Stream<OrderEvent> findByOrderId(Long orderId);
}
