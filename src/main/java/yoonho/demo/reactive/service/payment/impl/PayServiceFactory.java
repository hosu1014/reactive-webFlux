package yoonho.demo.reactive.service.payment.impl;

import yoonho.demo.reactive.service.payment.PayService;
import yoonho.demo.reactive.service.payment.PaymentMethod;

public class PayServiceFactory {

	public static PayService getInstance(PaymentMethod paymentMethod) {
		switch (paymentMethod) {
			case CARD : return new CardPayService();
			case BANK : return new BankPayService();
			default : return null;
		}
		
	}
}
