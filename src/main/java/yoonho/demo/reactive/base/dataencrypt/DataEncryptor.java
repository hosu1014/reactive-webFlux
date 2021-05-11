package yoonho.demo.reactive.base.dataencrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class DataEncryptor {
	private static DataEncryptManager dataEncryptManager;
	
	public static String encrypt(String value, EncryptType type) {
		switch(type) {
		case CARD_NO : 
			return dataEncryptManager.encryptCardNo(value);
		case ACCOUT_NO : 
			return dataEncryptManager.encryptAccountNo(value);
		default : 
			return dataEncryptManager.encryptAccountNo(value);
		}
	}
	
	public static String decrypt(String value, EncryptType type) {
		switch(type) {
		case CARD_NO : 
			return dataEncryptManager.decryptCardNo(value);
		case ACCOUT_NO : 
			return dataEncryptManager.decryptAccountNo(value);
		default : 
			return dataEncryptManager.decryptAccountNo(value);
		}
	}
	
	@Component 
	public static class Init {
		@Autowired
		void init(DataEncryptManager dataEncryptManager) {
			DataEncryptor.dataEncryptManager = dataEncryptManager;
		}
	}
}

