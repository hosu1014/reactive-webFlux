package yoonho.demo.reactive.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.auth.filter.BearerAuthenticationWebFilter;
import yoonho.demo.reactive.auth.filter.UriAuthorizationManager;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final BearerAuthenticationWebFilter authWebFilter;
	private final UriAuthorizationManager uriAuthorizationManager;
	
	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
		return http
			.exceptionHandling()
			.accessDeniedHandler((swe, e) -> {
				return Mono.fromRunnable(() -> {
					swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
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
