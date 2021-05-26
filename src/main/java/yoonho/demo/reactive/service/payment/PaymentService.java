package yoonho.demo.reactive.service.payment;

import java.math.BigDecimal;
import java.util.List;

import yoonho.demo.reactive.dto.PaymentBase;

public interface PaymentService {
	public void approval(List<PaymentBase> paymentReqList);
	public BigDecimal square(Long number);
}
