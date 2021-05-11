package yoonho.demo.reactive.service.auth.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.auth.PBKDF2Encoder;
import yoonho.demo.reactive.config.Messages;
import yoonho.demo.reactive.dto.auth.AuthRequest;
import yoonho.demo.reactive.dto.auth.AuthResponse;
import yoonho.demo.reactive.exception.UnauthorizedException;
import yoonho.demo.reactive.model.User;
import yoonho.demo.reactive.repository.MemberRepository;
import yoonho.demo.reactive.service.auth.UserService;
import yoonho.demo.reactive.util.JWTUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	private final MemberRepository memberRepository;
	private final PBKDF2Encoder passwordEncoder;
	private final JWTUtil jwtUtil;
	private final Messages messages;
	
	@Override
	public Mono<User> findByUserId(String userId) {
		return memberRepository.findByUserId(userId);
	}
	
	public Mono<Long> checkAuthority(String userId, String uri) {
		return memberRepository.checkAuthority(userId, uri);
	}
	
	public Mono<AuthResponse> login(AuthRequest ar) throws UnauthorizedException {
		return this.findByUserId(ar.getUserId())
				.defaultIfEmpty(new User())
				.map(user -> {
					if (ObjectUtils.isEmpty(user.getUserId())) {
						throw new UnauthorizedException(
								messages.getMessage("login.userId.empty", new String[] { ar.getUserId() }));
					}
					log.info("enc password is {}", passwordEncoder.encode(ar.getPassword()));
					if (user.getEncPassword().equals(passwordEncoder.encode(ar.getPassword()))) {
						return new AuthResponse(jwtUtil.generateToken(user));
					} else {
						throw new UnauthorizedException(
								messages.getMessage("login.passwd.error", new String[] { ar.getUserId() }));
					}
				});
	}
}
