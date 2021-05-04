package yoonho.demo.reactive.config;

import java.util.Locale;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.i18n.LocaleContextResolver;

/**
 * 다국어 지원을 위해 언어 설정시 @Configuration을 추가하면 message resource처리 가능 함. 
 * 단, Jackson2ObjectMapperBuilder의 formatter설정이 적용되지 않음. 
 * 차후 다시 확인하기로 하고 해당 설정을 빼놓는 것으로 함. 
 * 
 * 단점은 LocaleContextHolder에 저장이 되지 않고 ServerWebExchange의 LocaleContextResolver로 설정이 됨. 
 * MessageSource의 LocaleContext를 ServerWebExchange에서 get해야 의미가 있음. 
 *  
 * @author 정윤호
 *
 */

public class LocaleResolver extends DelegatingWebFluxConfiguration implements LocaleContextResolver {
	@Override
    protected LocaleContextResolver createLocaleContextResolver() {
        return new LocaleResolver();
    }
	
	@Override
    public LocaleContext resolveLocaleContext(ServerWebExchange exchange) {
        String language = exchange.getRequest().getQueryParams().getFirst("lang");
        Locale targetLocale = Locale.getDefault();
        if (language != null && !language.isEmpty()) {
            targetLocale = Locale.forLanguageTag(language);
        }
        
        return new SimpleLocaleContext(targetLocale);
    }

    @Override
    public void setLocaleContext(ServerWebExchange exchange, LocaleContext localeContext) {
    	throw new UnsupportedOperationException("Not Supported");
    }
}
