package yoonho.demo.reactive.config.repository;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;

import lombok.extern.slf4j.Slf4j;
import yoonho.demo.reactive.base.r2dbc.ReadWriteConverterHelper;
import yoonho.demo.reactive.model.User;

@WritingConverter
@Slf4j
public class UserrWritingConverter implements Converter<User, OutboundRow> {
	public OutboundRow convert(User source) {
		return ReadWriteConverterHelper.writeConvert(source);
	}
}