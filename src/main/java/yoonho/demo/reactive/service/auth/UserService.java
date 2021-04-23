package yoonho.demo.reactive.service.auth;

import reactor.core.publisher.Mono;
import yoonho.demo.reactive.dto.auth.AuthRequest;
import yoonho.demo.reactive.model.User;

public interface UserService {
	public Mono<User> findByUserId(String userId);
}
