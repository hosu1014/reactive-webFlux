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
	
	private DataEncryptor getDataEncryptor(EncryptType type) {
		switch(type) {
		case CARD_NO : 
			return cardNoDataEncryptor;
		case ACCOUT_NO : 
			return acntNoDataEncryptor;
		default : 
			return acntNoDataEncryptor;
		}
	}
	
	public String encrypt(String value, EncryptType type) {
		return getDataEncryptor(type).encrypt(value);
	}
	
	public String decrypt(String value, EncryptType type) {
		return getDataEncryptor(type).decrypt(value);
	}	
}

