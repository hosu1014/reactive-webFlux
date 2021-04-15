package yoonho.demo.reactive.service.payment;

import java.util.Arrays;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum Bank {
	KB("01", "국민은행"),
	HANA("02", "하나은행"),
	KAKAO("14", "카카오뱅크");
	
	private String bankCd;
	private String bankNm;
	
	Bank(String bankCd, String bankNm) {
		this.bankCd = bankCd;
		this.bankNm = bankNm;
	}
	
	public static Bank lookup(String cd) {
		return Arrays.stream(values())
			.filter(bank -> bank.getBankCd().equals(cd))
			.findAny()
			.get();
	}
	
}
