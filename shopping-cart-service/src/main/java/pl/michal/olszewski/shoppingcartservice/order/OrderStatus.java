package pl.michal.olszewski.shoppingcartservice.order;

public enum OrderStatus {
    PURCHASED(1L),
    PENDING(2L),
    CONFIRMED(3L),
    SHIPPED(4L),
    DELIVERED(5L);

    long value;

    OrderStatus(Long value) {
        this.value = value;
    }

}
