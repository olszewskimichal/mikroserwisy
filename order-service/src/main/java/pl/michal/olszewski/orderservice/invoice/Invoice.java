package pl.michal.olszewski.orderservice.invoice;

import lombok.Data;
import pl.michal.olszewski.orderservice.address.Address;
import pl.michal.olszewski.orderservice.order.Order;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue
    private Long invoiceId;
    private Long userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    @Embedded
    private Address billingAddress;

    private Long invoiceStatus;

}
