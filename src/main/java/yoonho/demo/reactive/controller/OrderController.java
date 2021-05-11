package yoonho.demo.reactive.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import yoonho.demo.reactive.dto.BankPaymentReq;
import yoonho.demo.reactive.dto.CardPaymentReq;
import yoonho.demo.reactive.dto.PaymentBase;
import yoonho.demo.reactive.service.payment.Bank;
import yoonho.demo.reactive.service.payment.CreditCard;
import yoonho.demo.reactive.service.payment.PaymentService;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
	private final PaymentService paymentService;
	
	@PostMapping("/order")
	public void order() {
		List<PaymentBase> paymentReqList = new ArrayList<PaymentBase>();
		
		
		CardPaymentReq cardPaymentReq = new CardPaymentReq();
		cardPaymentReq.setCreditCard(CreditCard.lookup("01"));
		cardPaymentReq.setAmmount(10000L);
		cardPaymentReq.setCardNo("9190****39391291");
		paymentReqList.add(cardPaymentReq);
		
		
		BankPaymentReq bankPaymentReq = new BankPaymentReq();
		bankPaymentReq.setBank(Bank.lookup("03"));
		bankPaymentReq.setAccountNo("1114564545");
		bankPaymentReq.setAmmount(20000L);
		paymentReqList.add(bankPaymentReq);
		
		paymentService.approval(paymentReqList);
		
	}
}
