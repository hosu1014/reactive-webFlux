package yoonho.demo.reactive.config;

import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@EnableR2dbcAuditing
@Slf4j
public class AuditConfig {
	@Bean
	public ReactiveAuditorAware<String> myAuditorProvider() {
		return new ReactiveAuditorAware<String>() {
			
			@Override
			public Mono<String> getCurrentAuditor() {
				return ReactiveSecurityContextHolder.getContext()
				        .filter(sc-> Objects.nonNull(sc.getAuthentication()))
				        .map(sc-> sc.getAuthentication())
				        .flatMap(authentication -> {
				        	if(Objects.nonNull(authentication.getPrincipal()) == false) 
				        		return Mono.empty();
				        	return Mono.just(authentication.getPrincipal().toString());
				        });
			}
		};
	}
}
