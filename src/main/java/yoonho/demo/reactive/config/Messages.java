package yoonho.demo.reactive.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class Messages {
	private final MessageSource messageSource;
	
	public String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocaleContext().getLocale());
    }
	
	public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocaleContext().getLocale());
    }
}
