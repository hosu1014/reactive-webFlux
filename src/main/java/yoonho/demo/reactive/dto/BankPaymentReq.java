package yoonho.demo.reactive.dto;

import lombok.Data;
import yoonho.demo.reactive.service.payment.Bank;
import yoonho.demo.reactive.service.payment.PaymentMethod;

@Data
public class BankPaymentReq extends PaymentBase {
	private Bank bank;
	private String accountNo;
	
	public BankPaymentReq() {
		super.setPaymentMethod(PaymentMethod.BANK);
	}
}
