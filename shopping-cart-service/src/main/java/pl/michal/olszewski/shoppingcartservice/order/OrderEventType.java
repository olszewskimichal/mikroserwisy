package pl.michal.olszewski.shoppingcartservice.order;

public enum OrderEventType {
    CREATED(1L),
    ORDERED(2L),
    RESERVED(3L),
    SHIPPED(4L),
    DELIVERED(5L);

    long value;

    OrderEventType(Long value) {
        this.value = value;
    }

}
