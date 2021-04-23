package yoonho.demo.reactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;
import yoonho.demo.reactive.model.User;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<User, Long> {

	public Mono<User> findByUserId(String userId);
}
