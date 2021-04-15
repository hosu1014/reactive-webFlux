package yoonho.demo.reactive.service.payment.impl;

import lombok.extern.slf4j.Slf4j;
import yoonho.demo.reactive.dto.ApprovedResponseBase;
import yoonho.demo.reactive.dto.BankApprovedResponse;
import yoonho.demo.reactive.dto.BankPaymentReq;
import yoonho.demo.reactive.dto.PaymentBase;
import yoonho.demo.reactive.service.payment.PayService;

@Slf4j
public class BankPayService extends PayService {
	@Override
	protected void logging(PaymentBase paymentBase) {
		BankPaymentReq paymentReq = (BankPaymentReq)paymentBase;
		log.info("은행명 : {}, 계좌번호 : {}, {}원 무통장입금 승인 처리", paymentReq.getBank().getBankNm(), paymentReq.getAccountNo(), paymentReq.getAmmount() );
	}
	
	@Override
	protected ApprovedResponseBase approvePay(PaymentBase paymentBase) {
		log.info("무통장 입금 승인처리");
		BankPaymentReq paymentReq = (BankPaymentReq)paymentBase;
		BankApprovedResponse bankApprovedResponse = new BankApprovedResponse();
		bankApprovedResponse.setBank(paymentReq.getBank());
		bankApprovedResponse.setStatusCode("SUCCESS");
		
		return bankApprovedResponse;
	}
	
	@Override
	protected void insert(PaymentBase paymentBase, ApprovedResponseBase approvedResponse) {
		log.info("무통장 입금 승인 결과처리");
		BankPaymentReq bankPaymentReq = (BankPaymentReq)paymentBase;
		BankApprovedResponse bankApprovedResponse = (BankApprovedResponse)approvedResponse;
		log.info("Request Info {}", bankPaymentReq);
		log.info("Response Info {}", bankApprovedResponse);
		
	}
	
}
