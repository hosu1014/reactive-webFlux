package yoonho.demo.reactive.base.dataencrypt;

import org.springframework.stereotype.Component;

import yoonho.demo.reactive.base.dataencrypt.impl.CardNoDataEncryptor;

@Component
public class DataEncryptorFactory {
	private final DataEncryptor cardNoDataEncryptor;
	private final DataEncryptor acntNoDataEncryptor;

	public DataEncryptorFactory() {
		this.cardNoDataEncryptor = new CardNoDataEncryptor();
		this.acntNoDataEncryptor = new CardNoDataEncryptor();
	}
	
	public String encrypt(String value, EncryptType type) {
		switch(type) {
		case CARD_NO : 
			return cardNoDataEncryptor.encrypt(value);
		case ACCOUT_NO : 
			return acntNoDataEncryptor.encrypt(value);
		default : 
			return acntNoDataEncryptor.encrypt(value);
		}
	}
	
	public String decrypt(String value, EncryptType type) {
		switch(type) {
		case CARD_NO : 
			return cardNoDataEncryptor.decrypt(value);
		case ACCOUT_NO : 
			return acntNoDataEncryptor.decrypt(value);
		default : 
			return acntNoDataEncryptor.decrypt(value);
		}
	}	
}

