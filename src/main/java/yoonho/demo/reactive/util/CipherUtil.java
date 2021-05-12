package yoonho.demo.reactive.util;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * In order to create a Cipher object, the application calls the Cipher's getInstance method, and passes the name of the requested transformation to it. Optionally, the name of a provider may be specified.
   A transformation is a string that describes the operation (or set of operations) to be performed on the given input, to produce some output. A transformation always includes the name of a cryptographic algorithm (e.g., AES), and may be followed by a feedback mode and padding scheme.
   A transformation is of the form:
	"algorithm/mode/padding" or
	"algorithm"
	(in the latter case, provider-specific default values for the mode and padding scheme are used). For example, the following is a valid transformation:
     	Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
 * @author 정윤호
 *
 */
@Slf4j
@Component
public class CipherUtil {
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String SECRET_KEY_ALGORITHM = "AES";
	private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
	@Value("${jjwt.password.encoder.secret}")
	private String secret;
	@Value("${jjwt.password.encoder.salt}")
	private String salt;
	@Value("${jjwt.password.encoder.iteration}")
	private Integer iteration;
	@Value("${jjwt.password.encoder.keylength}")
	private Integer keylength;
	private final byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	

	public String encrypt(String value) {
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
			KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), iteration, keylength);
			SecretKey tmp = factory.generateSecret(spec);

			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), SECRET_KEY_ALGORITHM);
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

			return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			log.error("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public String decrypt(String value) {
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
			KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), iteration, keylength);
			SecretKey tmp = factory.generateSecret(spec);

			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), SECRET_KEY_ALGORITHM);
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			
			return new String(cipher.doFinal(Base64.getDecoder().decode(value)));
		} catch (Exception e) {
			log.error("Error while decrypting: " + e.toString());
		}
		return null;
	}
}
