package yoonho.demo.reactive.auth;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationManager implements ReactiveAuthenticationManager  {
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		return Mono.just(authentication);
	}
}
