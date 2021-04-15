package yoonho.demo.reactive.dto;

import lombok.Data;
import yoonho.demo.reactive.service.payment.CreditCard;
import yoonho.demo.reactive.service.payment.PaymentMethod;

@Data
public class CardPaymentReq extends PaymentBase {
	private CreditCard creditCard; 
	private String cardNo;
	
	public CardPaymentReq() {
		super.setPaymentMethod(PaymentMethod.CARD);
	}
}
