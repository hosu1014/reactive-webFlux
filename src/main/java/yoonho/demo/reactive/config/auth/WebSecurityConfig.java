package yoonho.demo.reactive.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.auth.filter.BearerAuthenticationWebFilter;
import yoonho.demo.reactive.auth.filter.UriAuthorizationManager;
import yoonho.demo.reactive.exception.ForbiddenException;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {
	private final BearerAuthenticationWebFilter authWebFilter;
	private final UriAuthorizationManager uriAuthorizationManager;
	
	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
		return http
			.exceptionHandling()
			.accessDeniedHandler((swe, e) -> {
				return Mono.fromRunnable(() -> {
					throw new ForbiddenException(e.getMessage());
				});
			}).and()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.addFilterAt(authWebFilter.getWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
			.authorizeExchange()
			.pathMatchers(HttpMethod.OPTIONS).permitAll()
			.pathMatchers("/login", "/member/signUp").permitAll()
			.anyExchange().access(uriAuthorizationManager)
			.and().build();
	}
}
