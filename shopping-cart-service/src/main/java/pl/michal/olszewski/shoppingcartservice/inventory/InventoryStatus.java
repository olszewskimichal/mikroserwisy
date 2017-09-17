package pl.michal.olszewski.shoppingcartservice.inventory;

public enum InventoryStatus {
    IN_STOCK(1L),
    ORDERED(2L),
    RESERVED(3L),
    SHIPPED(4L),
    DELIVERED(5L);

    long value;

    InventoryStatus(Long value) {
        this.value = value;
    }

}
