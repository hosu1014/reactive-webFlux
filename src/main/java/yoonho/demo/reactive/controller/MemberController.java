package yoonho.demo.reactive.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.model.User;
import yoonho.demo.reactive.service.member.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/signUp")
	public Mono<User> signUp(@RequestBody User user) {
		
		return memberService.registMember(user);
	}
}
