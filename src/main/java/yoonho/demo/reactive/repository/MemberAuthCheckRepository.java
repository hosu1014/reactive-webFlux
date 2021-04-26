package yoonho.demo.reactive.repository;

import reactor.core.publisher.Mono;

public interface MemberAuthCheckRepository {
	public Mono<Long> checkAuthority(String userId, String uri);
}
