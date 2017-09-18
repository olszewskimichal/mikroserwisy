package pl.michal.olszewski.shoppingcartservice.cart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1")
@Slf4j
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping(path = "/events")
    public ResponseEntity addCartEvent(@RequestBody CartEvent cartEvent) throws Exception {
        log.info("Próba zapisu przez API cartEventu {}", cartEvent);
        return Optional.ofNullable(shoppingCartService.addCartEvent(cartEvent))
                .map(event -> new ResponseEntity(HttpStatus.NO_CONTENT))
                .orElseThrow(() -> new Exception("Could not find shopping cart"));
    }

    @PostMapping(path = "/checkout")
    public ResponseEntity checkoutCart() throws Exception {
        log.info("Próba POSTa checkoutu");
        return Optional.ofNullable(shoppingCartService.checkout())
                .map(checkoutResult -> new ResponseEntity<>(checkoutResult, HttpStatus.OK))
                .orElseThrow(() -> new Exception("Could not checkout"));
    }

    @GetMapping(path = "/cart")
    public ResponseEntity getCart() throws Exception {
        log.info("Próba odczytania przez API koszyka");
        return Optional.ofNullable(shoppingCartService.getShoppingCart())
                .map(cart -> new ResponseEntity<>(cart, HttpStatus.OK))
                .orElseThrow(() -> new Exception("Could not find shopping cart"));
    }
}
