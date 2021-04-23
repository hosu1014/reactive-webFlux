package yoonho.demo.reactive.service.auth.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.model.User;
import yoonho.demo.reactive.repository.MemberRepository;
import yoonho.demo.reactive.service.auth.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final MemberRepository memberRepository;
	
	@Override
	public Mono<User> findByUserId(String userId) {
		return memberRepository.findByUserId(userId);
		
	}
}
