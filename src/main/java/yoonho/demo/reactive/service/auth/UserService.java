package yoonho.demo.reactive.service.auth;

import reactor.core.publisher.Mono;
import yoonho.demo.reactive.dto.auth.AuthRequest;
import yoonho.demo.reactive.dto.auth.AuthResponse;
import yoonho.demo.reactive.model.User;

public interface UserService {
	public Mono<User> findByUserId(String userId);
	public Mono<Long> checkAuthority(String userId, String uri) ;
	public Mono<AuthResponse> login(AuthRequest ar) ;
}
