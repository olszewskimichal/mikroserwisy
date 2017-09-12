package pl.michal.olszewski.accountservice.credit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.michal.olszewski.accountservice.base.BaseEntity;
import pl.michal.olszewski.accountservice.unit.account.Account;

import javax.persistence.*;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
public class CreditCard extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String number;
    private Long creditCardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private Account account;

    public CreditCardType getCreditCardType() {
        return CreditCardType.fromValue(creditCardType);
    }
}
