package yoonho.demo.reactive.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.model.Cart;

@Repository
public interface CartRepository extends ReactiveCrudRepository<Cart, Long> {
	@Query("select nextval('cart_sq01') as id")
	public Mono<Long> getId(); 
	
	public Flux<Cart> findByTrNo(String trNo);
	public Flux<Cart> findBySpdNo(String spdNo);
}
