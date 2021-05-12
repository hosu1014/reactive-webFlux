package yoonho.demo.reactive.service.member;

import reactor.core.publisher.Mono;
import yoonho.demo.reactive.dto.UserAssetResponse;
import yoonho.demo.reactive.model.User;

public interface MemberService {

	public Mono<User> registMember(User user);

	public Mono<User> findMember(String userId);

	public Mono<User> update(User user);
	
	public Mono<UserAssetResponse> getUserAsset(String userId);
	
}
