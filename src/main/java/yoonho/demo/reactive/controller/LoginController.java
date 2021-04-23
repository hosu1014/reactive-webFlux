package yoonho.demo.reactive.controller;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.auth.PBKDF2Encoder;
import yoonho.demo.reactive.dto.auth.AuthRequest;
import yoonho.demo.reactive.dto.auth.AuthResponse;
import yoonho.demo.reactive.dto.auth.Message;
import yoonho.demo.reactive.service.auth.UserService;
import yoonho.demo.reactive.util.JWTUtil;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
	private final JWTUtil jwtUtil;
	private final PBKDF2Encoder passwordEncoder;
	private final UserService userService;
	
	private final Mono<SecurityContext> context  = ReactiveSecurityContextHolder.getContext();
	
	@PostMapping("/login")
	public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar) {
		log.info("encode password is [{}]", passwordEncoder.encode(ar.getPassword()));
		
		
		return userService.findByUserId(ar.getUserId())
				.map(user -> {
					if( user.getEncPassword().equals(passwordEncoder.encode(ar.getPassword()))) {
						return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(user)));
					} else {
						return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
					}
				})
				.defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	private Mono<String> getUserId() {
		return context
		        .filter(sc-> Objects.nonNull(sc.getAuthentication()))
		        .map(sc-> sc.getAuthentication())
		        .flatMap(authentication -> {
		        	if(Objects.nonNull(authentication.getPrincipal()) == false) 
		        		return Mono.empty();
		        	return Mono.just(authentication.getPrincipal().toString());
		        });
	}
	
	@RequestMapping(value = "/resource/user", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public  Mono<ResponseEntity<?>> user() {		
		return  getUserId().flatMap(userId-> Mono.just(ResponseEntity.ok(new Message(userId))));
	}
	
	@RequestMapping(value = "/resource/admin", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public Mono<ResponseEntity<?>> admin() {
		return Mono.just(ResponseEntity.ok(new Message("Content for admin")));
	}
	
	@RequestMapping(value = "/resource/user-or-admin", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Mono<ResponseEntity<?>> userOrAdmin() {
		return Mono.just(ResponseEntity.ok(new Message("Content for user or admin")));
	}
}
