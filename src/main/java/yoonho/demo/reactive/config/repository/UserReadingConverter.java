package yoonho.demo.reactive.config.repository;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import io.r2dbc.spi.Row;
import lombok.extern.slf4j.Slf4j;
import yoonho.demo.reactive.model.User;
import yoonho.demo.reactive.util.DataEncryptUtils;

@ReadingConverter
@Slf4j
public class UserReadingConverter implements Converter<Row, User> {
	public User convert(Row source) {
		User user = new User();
		DataEncryptUtils.readConvert(source, user);
		return user;
	}
}