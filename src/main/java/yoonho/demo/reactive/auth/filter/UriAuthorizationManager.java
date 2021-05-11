package yoonho.demo.reactive.auth.filter;

import java.util.Objects;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.service.auth.UserService;

@Slf4j
@Component
@RequiredArgsConstructor
public class UriAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
	private final UserService userService;
	
	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext ac) {
		
		ServerHttpRequest request = ac.getExchange().getRequest();
		return authentication.flatMap(auth -> {
			if(Objects.nonNull(auth.getPrincipal()) == false) 
	    		return Mono.just(new AuthorizationDecision(false));
			
			Mono<Long> authCount = userService.checkAuthority(auth.getPrincipal().toString(), request.getURI().getPath().toString());
			return authCount.flatMap(count -> {
				// boolean isAuth = count > 0L ? true : false;
				boolean isAuth = true;
				return Mono.just(new AuthorizationDecision(isAuth));
			});
		});
	}
}
