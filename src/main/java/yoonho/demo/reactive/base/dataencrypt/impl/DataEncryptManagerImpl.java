package yoonho.demo.reactive.base.dataencrypt.impl;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import yoonho.demo.reactive.base.dataencrypt.DataEncryptManager;
import yoonho.demo.reactive.util.CipherUtil;

@Component
@RequiredArgsConstructor
public class DataEncryptManagerImpl implements DataEncryptManager {
	final CipherUtil cipherUtil;
	private static final String binNoExcludeRegex = "[^\\d*]"; 
	private static final String actnNoExcludeRegex = "[^\\d]"; 
	private static final String binNoRegex= "([0-9*]{6})([0-9*]*)";
	private static final String binNoDecodeRegex= "([0-9*]{6})([\\w\\W]*)";
	
	@Override
	public String encryptCardNo(String cardNo) {
		Matcher binMatcher = Pattern.compile(binNoRegex).matcher(cardNo.replaceAll(binNoExcludeRegex, ""));
		
		if(binMatcher.matches()) {
			return binMatcher.group(1)
					.concat(cipherUtil.encrypt(binMatcher.group(2)));
		}
		return cardNo;
	}
	
	@Override
	public String decryptCardNo(String encCardNo) {
		Matcher binMatcher = Pattern.compile(binNoDecodeRegex).matcher(encCardNo);
		
		if(binMatcher.matches()) {
			return binMatcher.group(1)
					.concat(cipherUtil.decrypt(binMatcher.group(2)));
		}
		return encCardNo;
	}
	
	@Override
	public String encryptAccountNo(String acntNo) {
		return cipherUtil.encrypt(acntNo.replaceAll(actnNoExcludeRegex, ""));
	}
	
	@Override
	public String decryptAccountNo(String encAcntNo) {
		return cipherUtil.decrypt(encAcntNo);
	}
}
