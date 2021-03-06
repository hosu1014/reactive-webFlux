package yoonho.demo.reactive.config.repository;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import io.r2dbc.spi.Row;
import lombok.extern.slf4j.Slf4j;
import yoonho.demo.reactive.base.r2dbc.ReadWriteConverterHelper;
import yoonho.demo.reactive.model.Customer;

@ReadingConverter
@Slf4j
public class CustomerReadingConverter implements Converter<Row, Customer> {
	public Customer convert(Row source) {
		Customer customer = new Customer();
		ReadWriteConverterHelper.readConvert(source, customer);
		return customer;
	}
}