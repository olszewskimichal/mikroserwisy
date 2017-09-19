package pl.michal.olszewski.shoppingcartservice.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface CartEventRepository extends JpaRepository<CartEvent, Long> {
    List<CartEvent> findByUserId(Long userId);
}
