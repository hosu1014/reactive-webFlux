package yoonho.demo.reactive.service.member.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.auth.PBKDF2Encoder;
import yoonho.demo.reactive.model.User;
import yoonho.demo.reactive.repository.MemberRepository;
import yoonho.demo.reactive.service.member.MemberService;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final PBKDF2Encoder passwdEncoder;
	
	public Mono<User> registMember(User user) {
		user.setEncPassword(passwdEncoder.encode(user.getPassword()));
		user.setNewFlag(true);
		return memberRepository.save(user);
	}
}
