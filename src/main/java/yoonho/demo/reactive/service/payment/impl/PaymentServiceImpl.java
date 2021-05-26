package yoonho.demo.reactive.service.payment.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import yoonho.demo.reactive.dto.PaymentBase;
import yoonho.demo.reactive.service.payment.PaymentService;

@Service	
@Slf4j
public class PaymentServiceImpl implements PaymentService {

	@Override
	public void approval(List<PaymentBase> paymentReqList) {
		paymentReqList.stream().forEach(pb  -> {
			PayServiceFactory.getInstance(pb.getPaymentMethod()).approval(pb);
		});
	}
	
	
	@Cacheable(value = "squareCache", key = "#number", condition = "#number > 10")
	public BigDecimal square(Long number) {
		BigDecimal square = BigDecimal.valueOf(number).multiply(BigDecimal.valueOf(number));
        log.info("square of {} is {}", number, square);
        return square;
	}
}
