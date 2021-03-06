package pl.michal.olszewski.shoppingcartservice.order;

public enum OrderEventType {
    PURCHASED(1L),
    CREATED(2L),
    ORDERED(3L),
    SHIPPED(4L),
    DELIVERED(5L);

    long value;

    OrderEventType(Long value) {
        this.value = value;
    }

}
