package yoonho.demo.reactive.repository;

import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.model.Customer;

@Slf4j
@Component
public class DttmBeforeConvertCallback implements BeforeSaveCallback<Customer>, BeforeConvertCallback<Customer> {


	@Override
	public Publisher<Customer> onBeforeConvert(Customer entity, SqlIdentifier table) {
		// entity.setRegDttm(LocalDateTime.now());  // Audit Config등록으로 제외 처리 
		return Mono.just(entity);
	}
	
	@Override
	public Publisher<Customer> onBeforeSave(Customer entity, OutboundRow row, SqlIdentifier table) {
		log.info("before save customer : {}", entity);
		return Mono.just(entity);
	}
}
