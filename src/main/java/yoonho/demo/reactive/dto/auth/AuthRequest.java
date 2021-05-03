package yoonho.demo.reactive.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class AuthRequest {
	private String userId;
	private String password;
}
