package yoonho.demo.reactive.service.cart;

import java.util.List;

import reactor.core.publisher.Flux;
import yoonho.demo.reactive.dto.Cart.CartRes;
import yoonho.demo.reactive.dto.Cart.InsertCartReq;
import yoonho.demo.reactive.model.Cart;

public interface CartService {
	public Flux<Cart> addCart(List<InsertCartReq> inserCartReqList);
	public Flux<Cart> addCart2(List<InsertCartReq> inserCartReqList);
	
	public Flux<CartRes> getList();
	public Flux<CartRes> getList2();
	public Flux<CartRes> getListByTrNo(String trNo);
	public Flux<CartRes> getListBySpdNo(String spdNo);

}
