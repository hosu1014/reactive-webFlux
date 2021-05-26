package yoonho.demo.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@EnableCaching
@Slf4j
public class ReactiveWebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWebfluxApplication.class, args);
	}
	
	@GetMapping("/hello")
	public Mono<String> hello() {
		return Mono.just("hello");
	}

}
