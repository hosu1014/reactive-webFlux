package yoonho.demo.reactive.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.dto.Cart.CartRes;
import yoonho.demo.reactive.dto.Cart.InsertCartReq;
import yoonho.demo.reactive.dto.Cart.UpdateCartReq;
import yoonho.demo.reactive.model.Cart;
import yoonho.demo.reactive.repository.CartRepository;
import yoonho.demo.reactive.service.cart.CartService;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
	private final CartService cartService;
	private final CartRepository cartRepository;
	
	@PostMapping("/addCart")
	public Flux<Cart> insertCart(@RequestBody List<InsertCartReq> cartReqList) {
		return cartService.addCart(cartReqList);
	}

	@PostMapping("/addCart2")
	public Flux<Cart> insertCart2(@RequestBody List<InsertCartReq> cartReqList) {
		return cartService.addCart2(cartReqList);
	}
	
	@PostMapping("/addCart3")
	public Mono<Cart> insertCart3(@RequestBody Cart cart) {
		cart.setNewFlag(true);
		log.info("cart is {}", cart);
		return cartRepository.save(cart).log();
	}
	
	
	@GetMapping("/list")
	public Flux<CartRes> getCartList() {
		return cartService.getList();
	}
	
	@GetMapping("/list2")
	public Flux<CartRes> getCartList2() {
		return cartService.getList2();
	}
	
	@GetMapping("/list/{trNo}")
	public Flux<CartRes> getCartList(@PathVariable String trNo) {
		return cartService.getListByTrNo(trNo);
	}
	
	@GetMapping("/list/spd/{spdNo}")
	public Flux<CartRes> getCartListBySpdNo(@PathVariable String spdNo) {
		return cartService.getListBySpdNo(spdNo);
	}
	
	@PutMapping("updateCnt") 
	public Mono<Cart> updateCnt(@RequestBody UpdateCartReq updateCartReq) {
		return cartRepository
				.findById(updateCartReq.getCartSn())
				.log()
				.map(cart -> {
					cart.setOdQty(updateCartReq.getOdQty());
					return cart;
				})
				.flatMap(cart -> cartRepository.save(cart)); 
	}
}
