package yoonho.demo.reactive.service.payment;

public enum PaymentMethod {
	
	CARD("카드"),
	BANK("쿠통장입금"),
	ACCOUNT("실시간계좌이체");
	
	private String name;
	
	PaymentMethod(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
}
