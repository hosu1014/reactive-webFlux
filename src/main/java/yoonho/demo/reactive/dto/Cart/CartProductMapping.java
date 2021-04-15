package yoonho.demo.reactive.dto.Cart;

import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Flux;
import yoonho.demo.reactive.dto.product.Item;
import yoonho.demo.reactive.model.Cart;

@Getter @Setter
public class CartProductMapping {
	private String groupedKey;
	private Flux<Cart> cartFlux;
	private Flux<Item> itemFlux;
}
