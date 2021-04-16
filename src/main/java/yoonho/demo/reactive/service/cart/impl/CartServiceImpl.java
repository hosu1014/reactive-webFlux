package yoonho.demo.reactive.service.cart.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import yoonho.demo.reactive.dto.Cart.CartProductMapping;
import yoonho.demo.reactive.dto.Cart.CartRes;
import yoonho.demo.reactive.dto.Cart.InsertCartReq;
import yoonho.demo.reactive.dto.product.Item;
import yoonho.demo.reactive.dto.product.ProductReq;
import yoonho.demo.reactive.exapi.product.ProductApi;
import yoonho.demo.reactive.model.Cart;
import yoonho.demo.reactive.repository.CartRepository;
import yoonho.demo.reactive.service.cart.CartService;

@RequiredArgsConstructor
@Service
@Slf4j
public class CartServiceImpl implements CartService {
	private final ProductApi productApi;
	private final CartRepository cartRepository;
	
	@Override
	@Transactional
	public Flux<Cart> addCart(List<InsertCartReq> inserCartReqList) {
		List<ProductReq> productReqList = groupedProductReqList(inserCartReqList);
		Flux<Item> itemFlux = productApi.getProductList(productReqList);
		
		return Flux.zip(Flux.fromIterable((productReqList)), 
				itemFlux, 
				(req, res) -> {
					if(StringUtils.isEmpty(res.getReturnCode())) return null;
					Cart cart = new Cart();
					BeanUtils.copyProperties(req, cart);
					BeanUtils.copyProperties(res, cart);
					cart.setNewFlag(true);		
					return cart;
					}
				).filter(c->!ObjectUtils.isEmpty(c))
				.flatMap(cart -> 	cartRepository.save(cart));
	}
	
	@Override
	@Transactional
	public Flux<Cart> addCart2(List<InsertCartReq> inserCartReqList) {
		List<ProductReq> productReqList = groupedProductReqList(inserCartReqList);
		return Flux.fromIterable(productReqList)
			.concatMap(productReq -> {
				Flux<Item> itemFlux = productApi.getProduct(productReq);
						
				return Flux.zip(Flux.just(productReq), 
						itemFlux, 
						(req, res) -> {
							Cart cart = new Cart();
							BeanUtils.copyProperties(req, cart);
							BeanUtils.copyProperties(res, cart);
							cart.setNewFlag(true);		
							return cart;
							}
						);
			}).flatMap(cart -> 	cartRepository.save(cart));
			
			
	}

	// 상품요청 정보를 그룹핑해서 요청 수량 sub
	private List<ProductReq> groupedProductReqList(List<InsertCartReq> inserCartReqList) {
		return inserCartReqList.stream()
				.collect(Collectors.groupingBy(cartReq-> cartReq.getGroupedKey()))
				.entrySet().stream()
				.map(e -> e.getValue()
							.stream()
							.reduce((acc, cur) -> new InsertCartReq(acc.getSpdNo(), acc.getSitmNo(), acc.getOdQty() + cur.getOdQty()))
						)
				.map(op -> {
					ProductReq productReq = new ProductReq();
					BeanUtils.copyProperties(op.get(), productReq);
					return productReq;
				})
				.collect(Collectors.toList());
	}
	
	@Override
	public Flux<CartRes> getList() {
		Flux<Cart> cartFlux = cartRepository.findAll();
		return cartFlux.concatMap(cart -> {
			Flux<Item> itemFlux = productApi.getProduct(ProductReq.getProductReqByGroupedKey(cart.getGroupedKey()));
			
			return Flux.zip(Flux.just(cart), 
					itemFlux, 
					(req, res) -> {
						CartRes cartRes = new CartRes();
						BeanUtils.copyProperties(req, cartRes);
						cartRes.setItem(res);		
						return cartRes;}
					);
		})
		.sort((cart1, cart2) -> cart2.getRegDttm().compareTo(cart1.getRegDttm()))
		.sort((cart1, cart2) -> cart1.getTrNo().compareTo(cart2.getTrNo()));
	}
	
	
	public Flux<CartRes> getList2() {
		Flux<Cart> cartFlux = cartRepository.findAll();
		return aggregationCartList(cartFlux);
	}

	public Flux<CartRes> getListByTrNo(String trNo) {
		Flux<Cart> cartFlux = cartRepository.findByTrNo(trNo);
		return aggregationCartList(cartFlux);
	}
	
	public Flux<CartRes> getListBySpdNo(String spdNo) {
		Flux<Cart> cartFlux = cartRepository.findBySpdNo(spdNo);
		return aggregationCartList(cartFlux);
	}
	
	private Flux<CartRes> aggregationCartList(Flux<Cart> cartFlux) {
		return cartFlux.cache()
		.groupBy(cartReq -> new ProductReq(cartReq.getSpdNo(), cartReq.getSitmNo(), cartReq.getLrtrNo(), 1))
		.concatMap(g -> {
			Flux<Item> itemFlux = productApi.getProduct(g.key());
			
			CartProductMapping cartProductMapping = new CartProductMapping();
			cartProductMapping.setGroupedKey(g.key());
			cartProductMapping.setCartFlux(g);
			cartProductMapping.setItemFlux(itemFlux);
			return Flux.just(cartProductMapping);
		}).flatMap(cpm -> {
			return Flux.zip(cpm.getItemFlux().cache().repeat()
					, cpm.getCartFlux()
					, (item, cart) -> {
						CartRes cartRes = new CartRes();
						BeanUtils.copyProperties(cart, cartRes);
						cartRes.setItem(item);
						return cartRes;}
					);

		})
		.sort((cart1, cart2) -> cart1.getRegDttm().compareTo(cart2.getRegDttm()) * -1)
		.sort((cart1, cart2) -> cart1.getTrNo().compareTo(cart2.getTrNo()))
		;	
	}
	
	private Flux<CartRes> aggregationCartList2(Flux<Cart> cartFlux) {
		return cartFlux.cache()
				.groupBy(cart ->ProductReq.builder()
											.spdNo(cart.getSpdNo())
											.sitmNo(cart.getSitmNo())
											.lrtrNo(cart.getLrtrNo())
											.build()) // 그룹핑 조건(상품)
				.concatMap(groups -> {
					return productApi.getProduct(groups.key())
							.flatMap(item -> {
								return groups.flatMap(cart -> {
									CartRes cartRes = new CartRes();
									BeanUtils.copyProperties(cart, cartRes);
									cartRes.setItem(item);
									return Flux.just(cartRes);
								});
							});
				})
				.sort((cart1, cart2) -> cart1.getRegDttm().compareTo(cart2.getRegDttm()) * -1)
				.sort((cart1, cart2) -> cart1.getTrNo().compareTo(cart2.getTrNo()))
				;	
	}
}
