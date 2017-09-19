package pl.michal.olszewski.orderservice.credit;

import lombok.Data;
import pl.michal.olszewski.orderservice.account.Account;

import java.io.Serializable;

@Data
public class CreditCard implements Serializable {
    private Long id;
    private String number;
    private CreditCardType creditCardType;
    private Long createdAt;
    private Long lastModified;
    private Account account;

}
