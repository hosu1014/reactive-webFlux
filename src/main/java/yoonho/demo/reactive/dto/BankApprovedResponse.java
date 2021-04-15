package yoonho.demo.reactive.dto;

import lombok.Getter;
import lombok.Setter;
import yoonho.demo.reactive.service.payment.Bank;

@Getter
@Setter
public class BankApprovedResponse extends ApprovedResponseBase {
	private Bank bank;
	private String statusCode;
}
