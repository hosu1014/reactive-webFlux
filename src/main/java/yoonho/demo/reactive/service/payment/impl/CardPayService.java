package yoonho.demo.reactive.service.payment.impl;

import lombok.extern.slf4j.Slf4j;
import yoonho.demo.reactive.dto.ApprovedResponseBase;
import yoonho.demo.reactive.dto.CardApprovedResponse;
import yoonho.demo.reactive.dto.CardPaymentReq;
import yoonho.demo.reactive.dto.PaymentBase;
import yoonho.demo.reactive.service.payment.PayService;

@Slf4j
public class CardPayService extends PayService {

	@Override
	protected void logging(PaymentBase paymentBase) {
		CardPaymentReq cardPaymentReq = (CardPaymentReq)paymentBase;
		log.info("카드사 : {}, 카드번호 : {}, {}원 카드승인 처리", cardPaymentReq.getCreditCard().getNm(),  cardPaymentReq.getCardNo(), cardPaymentReq.getAmmount() );
	}
	
	@Override
	protected ApprovedResponseBase approvePay(PaymentBase paymentBase) {
		log.info("카드 승인처리");
		CardPaymentReq cardPaymentReq = (CardPaymentReq)paymentBase;
		
		CardApprovedResponse cardApprovedResponse = new CardApprovedResponse();
		cardApprovedResponse.setCreditCard(cardPaymentReq.getCreditCard());
		cardApprovedResponse.setResultCode("0000");
		cardApprovedResponse.setApproveNo("승인번호39393939");
		cardApprovedResponse.setTID("거래번호393939");
		return cardApprovedResponse;
	}
	
	
	@Override
	protected void insert(PaymentBase paymentBase, ApprovedResponseBase approvedResponse) {
		log.info("카드 승인 결과처리");
		CardPaymentReq cardPaymentReq = (CardPaymentReq)paymentBase;
		CardApprovedResponse cardApprovedResponse = (CardApprovedResponse)approvedResponse;
		log.info("Request Info {}", cardPaymentReq);
		log.info("Response Info {}", cardApprovedResponse);
		
	}
	

}
