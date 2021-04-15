package yoonho.demo.reactive.dto.Cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartReq {
	private Long cartSn;
	private Integer odQty;

}
