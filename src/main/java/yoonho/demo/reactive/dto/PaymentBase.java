package yoonho.demo.reactive.dto;

import lombok.Getter;
import lombok.Setter;
import yoonho.demo.reactive.service.payment.PaymentMethod;

@Getter
@Setter
public class PaymentBase {
	private PaymentMethod paymentMethod;
	private Long ammount;
}
