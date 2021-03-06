package pl.michal.olszewski.accountservice.unit.account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.michal.olszewski.accountservice.address.Address;
import pl.michal.olszewski.accountservice.base.BaseEntity;
import pl.michal.olszewski.accountservice.credit.CreditCard;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Account extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "ship_street")),
            @AttributeOverride(name = "state", column = @Column(name = "ship_state")),
            @AttributeOverride(name = "city", column = @Column(name = "ship_city")),
            @AttributeOverride(name = "country", column = @Column(name = "ship_country")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "ship_zipCode"))})
    private Address shippingAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "billing_street")),
            @AttributeOverride(name = "state", column = @Column(name = "billing_state")),
            @AttributeOverride(name = "city", column = @Column(name = "billing_city")),
            @AttributeOverride(name = "country", column = @Column(name = "billing_country")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "billing_zipCode"))})
    private Address billingAddress;
    private String userName;
    private String accountNumber;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CreditCard> creditCards = new HashSet<>();

    public Account(Address shippingAddress, Address billingAddress, String userName, String accountNumber) {
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.userName = userName;
        this.accountNumber = accountNumber;
    }

    public void addCreditCard(CreditCard creditCard) {
        creditCards.add(creditCard);
        creditCard.setAccount(this);
    }

    public void removeCreditCard(CreditCard creditCard) {
        creditCards.remove(creditCard);
        creditCard.setAccount(null);
    }
}
