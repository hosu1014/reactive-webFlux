package yoonho.demo.reactive.base.dataencrypt.impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import yoonho.demo.reactive.base.dataencrypt.DataEncryptManager;
import yoonho.demo.reactive.util.CipherUtil;

@Component
@RequiredArgsConstructor
public class DataEncryptManagerImpl implements DataEncryptManager {
	private final CipherUtil cipherUtil;
	
	@Override
	public String encryptCardNo(String cardNo) {
		return cipherUtil.encrypt(cardNo);
	}
	
	@Override
	public String decryptCardNo(String encCardNo) {
		return cipherUtil.decrypt(encCardNo);
	}
	
	@Override
	public String encryptAccountNo(String acntNo) {
		return cipherUtil.encrypt(acntNo);
	}
	
	@Override
	public String decryptAccountNo(String encAcntNo) {
		return cipherUtil.decrypt(encAcntNo);
	}
}
