package yoonho.demo.reactive.dto;

import lombok.Getter;
import lombok.Setter;
import yoonho.demo.reactive.service.payment.CreditCard;

@Getter
@Setter
public class CardApprovedResponse extends ApprovedResponseBase {
	private CreditCard creditCard;
	private String TID;
	private String approveNo;
	private String resultCode;
}
