package yoonho.demo.reactive.auth;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PBKDF2Encoder implements PasswordEncoder {
	
	@Value("${jjwt.password.encoder.salt}")
	private String salt;
	@Value("${jjwt.password.encoder.iteration}")
	private Integer iteration;
	@Value("${jjwt.password.encoder.keylength}")
	private Integer keylength;
	
	@Override
	public String encode(CharSequence rawPassword) {
		try {
			byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
					.generateSecret(new PBEKeySpec(rawPassword.toString().toCharArray(), salt.getBytes(), iteration, keylength))
					.getEncoded();
			return Base64.getEncoder().encodeToString(result);
		} catch(NoSuchAlgorithmException | InvalidKeySpecException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encode(rawPassword).equals(encodedPassword);
	}
}
