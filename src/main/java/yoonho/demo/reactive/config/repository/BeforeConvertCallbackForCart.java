package yoonho.demo.reactive.config.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.r2dbc.core.DatabaseClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.model.Cart;

@Configuration
@Slf4j
public class BeforeConvertCallbackForCart {
	@Bean
	BeforeConvertCallback<Cart> CartSnGeneratingCallback(DatabaseClient databaseClient) {
		return (cart, sqlIdentifier) -> {
			if (cart.getId() == null) {
				log.info("before convert call back : {}", cart);
				return databaseClient.sql("select nextval('cart_sq01')")
						.map(row -> row.get(0, Long.class))
						.first() 
						.map(cart::withId);
			}
			return Mono.just(cart);
		};
	}
}
