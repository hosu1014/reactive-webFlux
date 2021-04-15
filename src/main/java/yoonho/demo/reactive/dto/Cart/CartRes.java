package yoonho.demo.reactive.dto.Cart;

import lombok.Getter;
import lombok.Setter;
import yoonho.demo.reactive.dto.product.Item;
import yoonho.demo.reactive.model.Cart;

@Getter @Setter
public class CartRes extends Cart {
	private Item item;
}
