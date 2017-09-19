package pl.michal.olszewski.accountservice.credit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.michal.olszewski.accountservice.base.BaseEntity;
import pl.michal.olszewski.accountservice.unit.account.Account;

import javax.persistence.*;

@Entity
@EqualsAndHashCode(callSuper = false, exclude = "account")
@Data
@ToString(exclude = "account")
public class CreditCard extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String number;
    private Long creditCardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    @JsonIgnore
    private Account account;

    public CreditCardType getCreditCardType() {
        return CreditCardType.fromValue(creditCardType);
    }
}
