package yoonho.demo.reactive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.dto.UserAssetDtoResponse;
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
	
	@GetMapping("/find/{userId}")
	public Mono<User> find(@PathVariable String userId) {
		return memberService.findMember(userId);
	}
	
	@GetMapping("/asset/{userId}")
	public Mono<UserAssetDtoResponse> getUerAsset(@PathVariable String userId) {
		return memberService.getUserAsset(userId);
	}
	
	@PutMapping("/update")
	public Mono<User> update(@RequestBody User user) {
		return memberService.findMember(user.getUserId())
				.map(u -> {
					u.setCcrdNo(user.getCcrdNo());
					u.setActnNo(user.getActnNo());
					return u;
				})
				.flatMap(u -> memberService.update(u))
				;	
	}
}
