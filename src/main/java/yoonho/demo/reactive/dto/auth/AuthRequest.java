package yoonho.demo.reactive.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {
	private String userId;
	private String password;
}
