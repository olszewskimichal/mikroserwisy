package pl.michal.olszewski.shoppingcartservice.cart;

public enum CartEventType {
    ADD_ITEM(1L),
    REMOVE_ITEM(2L),
    CLEAR_CART(3L),
    CHECKOUT(4L);

    long value;

    CartEventType(Long value) {
        this.value = value;
    }

    public static CartEventType fromValue(long type) {
        for (CartEventType cardType : CartEventType.values()) {
            if (cardType.value == type)
                return cardType;
        }
        return null;
    }

    public long getValue() {
        return value;
    }
}
