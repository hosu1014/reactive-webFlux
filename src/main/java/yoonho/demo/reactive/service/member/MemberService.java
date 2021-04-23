package yoonho.demo.reactive.service.member;

import reactor.core.publisher.Mono;
import yoonho.demo.reactive.model.User;

public interface MemberService {

	public Mono<User> registMember(User user);
	
}
