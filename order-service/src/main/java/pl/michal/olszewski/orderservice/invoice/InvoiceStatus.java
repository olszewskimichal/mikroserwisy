package pl.michal.olszewski.orderservice.invoice;

public enum InvoiceStatus {
    CREATED(1L),
    SENT(2L),
    PAID(3L);

    long value;

    InvoiceStatus(Long value) {
        this.value = value;
    }

}
