package yoonho.demo.reactive.service.payment;

import lombok.extern.slf4j.Slf4j;
import yoonho.demo.reactive.dto.ApprovedResponseBase;
import yoonho.demo.reactive.dto.PaymentBase;

@Slf4j
public abstract class PayService {
	public void approval(PaymentBase paymentBase) {
		logging(paymentBase);
		ApprovedResponseBase approvedResponse = approvePay(paymentBase);
		insert(paymentBase, approvedResponse);
	};
	public void cancel(PaymentBase paymentBase) {
		log.info("취소처리");
	}
	
	
	protected abstract void logging(PaymentBase paymentBase);
	protected abstract ApprovedResponseBase approvePay(PaymentBase paymentBase);
	protected abstract void insert(PaymentBase paymentBase, ApprovedResponseBase approvedResponse);
}
