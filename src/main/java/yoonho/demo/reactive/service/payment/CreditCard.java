package yoonho.demo.reactive.service.payment;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum CreditCard {
	KB("01", "국민카드"), 
	LOTTE("02", "롯데"), 
	HYUNDAI("04", "현대");

	private String cd;
	private String nm;

	CreditCard(String cd, String nm) {
		this.cd = cd;
		this.nm = nm;
	}

	public static CreditCard lookup(String cd) {
		return Arrays.stream(values())
				.filter(card -> card.getCd().equals(cd))
				.findAny()
				.get();
	}

}
