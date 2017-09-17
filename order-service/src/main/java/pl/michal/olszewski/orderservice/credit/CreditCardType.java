package pl.michal.olszewski.orderservice.credit;

public enum CreditCardType {

    VISA(1L),
    MASTERCARD(2L),
    AMERICAN_EXPRESS(3L),
    UNKNOWN(4L);
    long value;

    private CreditCardType(Long value) {
        this.value = value;
    }

    public static CreditCardType fromValue(long creditCardType) {
        for (CreditCardType cardType : CreditCardType.values()) {
            if (cardType.value == creditCardType)
                return cardType;
        }
        return UNKNOWN;
    }
}
