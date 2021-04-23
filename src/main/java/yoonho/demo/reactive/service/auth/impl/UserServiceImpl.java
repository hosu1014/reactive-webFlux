package yoonho.demo.reactive.service.auth.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import yoonho.demo.reactive.auth.Role;
import yoonho.demo.reactive.model.User;
import yoonho.demo.reactive.service.auth.UserService;

@Service
public class UserServiceImpl implements UserService {
private Map<String, User> data;
	
	@PostConstruct
	public void init(){
		data = new HashMap<>();
		
		//username:passwowrd -> user:user
		data.put("user", new User("user", "JQ7sS6oFMxpDg/WvmH3eX7KU/yAXR6phq44jUoFU69s=", true, Arrays.asList(Role.ROLE_USER)));

		//username:passwowrd -> admin:admin
		data.put("admin", new User("admin", "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=", true, Arrays.asList(Role.ROLE_ADMIN)));
	}
	
	@Override
	public Mono<User> findByUsername(String username) {
		if (data.containsKey(username)) {
			return Mono.just(data.get(username));
		} else {
			return Mono.empty();
		}
	}
}
