package yoonho.demo.reactive.config.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.r2dbc.core.DatabaseClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.model.User;

@Configuration
@Slf4j
public class MemberBeforeConvertCallback {
	@Bean
	BeforeConvertCallback<User> memberNoGeneratingCallback(DatabaseClient databaseClient) {
		return (user, sqlIdentifier) -> {
			if (user.getId() == null) {
				return databaseClient.sql("select nextval('member_sq01')")
						.map(row -> row.get(0, Long.class))
						.first() 
						.map(user::withId);
			}
			return Mono.just(user);
		};
	}
}
