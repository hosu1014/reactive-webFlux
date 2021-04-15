package yoonho.demo.reactive.config.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.r2dbc.core.DatabaseClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.model.Customer;

@Configuration
@Slf4j
public class BeforeConvertCallbackForCustomer {
	@Bean
	BeforeConvertCallback<Customer> idGeneratingCallback(DatabaseClient databaseClient) {
		return (customer, sqlIdentifier) -> {
			if (customer.getId() == null) {
				log.info("before convert call back : {}", customer);
				return databaseClient.sql("select nextval('customer_sq01')")
						.map(row -> row.get(0, Long.class))
						.first() 
						.map(customer::withId);
			}
			return Mono.just(customer);
		};
	}
}
