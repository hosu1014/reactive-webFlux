package yoonho.demo.reactive.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class Messages {
	private final MessageSource messageSource;
	
	public String getMessage(String code) {
        return this.getMessage(code, null, Locale.getDefault());
    }
	
	public String getMessage(String code, ServerWebExchange se) {
        return this.getMessage(code, null, se.getLocaleContext().getLocale());
    }
	
	public String getMessage(String code, Object[] args) {
        return this.getMessage(code, args, Locale.getDefault());
    }
	public String getMessage(String code, Object[] args, ServerWebExchange se) {
		return this.getMessage(code, args, se.getLocaleContext().getLocale());
	}
	
	public String getMessage(String code, Object[] args, Locale locale) {
		return messageSource.getMessage(code, args, locale);
	}
	
	
}
