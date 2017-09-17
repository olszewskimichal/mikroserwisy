package pl.michal.olszewski.orderservice.account;

import lombok.Data;
import pl.michal.olszewski.orderservice.address.Address;
import pl.michal.olszewski.orderservice.credit.CreditCard;

import java.io.Serializable;
import java.util.Set;


@Data
public class Account implements Serializable {
    private Long id;
    private Address shippingAddress;
    private Address billingAddress;
    private String userName;
    private Set<CreditCard> creditCards;
}
