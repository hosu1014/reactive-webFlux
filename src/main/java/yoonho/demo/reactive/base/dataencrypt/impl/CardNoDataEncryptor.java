package yoonho.demo.reactive.base.dataencrypt.impl;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yoonho.demo.reactive.base.dataencrypt.DataEncryptor;
import yoonho.demo.reactive.util.CipherUtil;

public class CardNoDataEncryptor implements DataEncryptor {
	private static final String binNoExcludeRegex = "[^\\d*]"; 
	private static final String binNoRegex= "([0-9*]{6})([0-9*]*)";
	private static final String binNoDecodeRegex= "([0-9*]{6})([\\w\\W]*)";
	
	@Override
	public String encrypt(String value) {
		Matcher binMatcher = Pattern.compile(binNoRegex).matcher(value.replaceAll(binNoExcludeRegex, ""));
		
		if(binMatcher.matches()) {
			return binMatcher.group(1)
					.concat(CipherUtil.encrypt(binMatcher.group(2)));
		}
		return value;
	}
	
	@Override
	public String decrypt(String value) {
		Matcher binMatcher = Pattern.compile(binNoDecodeRegex).matcher(value);
		
		if(binMatcher.matches()) {
			return binMatcher.group(1)
					.concat(CipherUtil.decrypt(binMatcher.group(2)));
		}
		return value;
	}
}
