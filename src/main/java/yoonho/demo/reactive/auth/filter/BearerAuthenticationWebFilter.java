package yoonho.demo.reactive.auth.filter;

import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.http.HttpHeaders;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.auth.AuthenticationManager;
import yoonho.demo.reactive.util.JWTUtil;

@Component
@RequiredArgsConstructor
@Slf4j
public class BearerAuthenticationWebFilter {
	private final AuthenticationManager authenticationManager;
	private final JWTUtil jwtUtil;
	
	private static final String BEARER = "Bearer ";
	private static final Predicate<String> matchBearerLength = authValue -> authValue.length() > BEARER.length();
	private static final Function<String, Mono<String>> isolateBearerValue = authValue -> Mono.justOrEmpty(authValue.substring(BEARER.length()));
	
	  
	public WebFilter getWebFilter() {
		AuthenticationWebFilter authWebFilter = new AuthenticationWebFilter(authenticationManager);
		authWebFilter.setServerAuthenticationConverter((serverWebExchange) -> {
			return Mono.justOrEmpty(serverWebExchange.getRequest()
				    .getHeaders()
				    .getFirst(HttpHeaders.AUTHORIZATION))
				.filter(matchBearerLength)
				.flatMap(isolateBearerValue)
				.flatMap(authToken -> new CurrentUserAuthenticationToken(jwtUtil).create(authToken))
				;
		});
		
		return authWebFilter;
	}
	 
}
