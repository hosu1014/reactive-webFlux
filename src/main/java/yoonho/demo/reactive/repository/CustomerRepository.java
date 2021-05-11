package yoonho.demo.reactive.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.model.Customer;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long>{
	
	@Query("select nextval('customer_sq01') as id")
	public Mono<Long> getId(); 
	
	public Flux<Customer> findByName(String name);

}
