package yoonho.demo.reactive.base.dataencrypt.impl;


import yoonho.demo.reactive.base.dataencrypt.DataEncryptor;
import yoonho.demo.reactive.util.CipherUtil;

public class ActnNoDataEncryptor implements DataEncryptor {
	private static final String actnNoExcludeRegex = "[^\\d]"; 
	
	@Override
	public String encrypt(String value) {
		return CipherUtil.encrypt(value.replaceAll(actnNoExcludeRegex, ""));
	}
	
	@Override
	public String decrypt(String value) {
		return CipherUtil.decrypt(value);
	}
	
}
