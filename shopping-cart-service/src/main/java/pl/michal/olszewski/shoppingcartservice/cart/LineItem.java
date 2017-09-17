package pl.michal.olszewski.shoppingcartservice.cart;

import lombok.Data;
import pl.michal.olszewski.shoppingcartservice.product.Product;

@Data
public class LineItem {
    //TODO poprawic by był tylko id lub tylko product

    private Long productId;
    private Product product;
    private Integer quantity;

    public LineItem(Long productId, Product product, Integer quantity) {
        this.productId = productId;
        this.product = product;
        this.quantity = quantity;
    }
}
