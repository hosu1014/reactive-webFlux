package yoonho.demo.reactive.config;

import java.util.Locale;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.adapter.HttpWebHandlerAdapter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import org.springframework.web.server.i18n.LocaleContextResolver;

import lombok.extern.slf4j.Slf4j;

/**
 * 다국어 설정 테스트를 위해 설정한 config임. 
 * 최초 로딩시 한번만 적용이 되고 이후는 적용 되지 않음.   
 * @author 정윤호
 *
 */

@Slf4j
public class LocaleContextHandler {

	@Bean
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
	public HttpHandler httpHandler(ApplicationContext applicationContext) {
		HttpHandler delegate = WebHttpHandlerBuilder.applicationContext(applicationContext).build();
		return new HttpWebHandlerAdapter(((HttpWebHandlerAdapter) delegate)) {
			@Override
			protected ServerWebExchange createExchange(ServerHttpRequest request, ServerHttpResponse response) {
				ServerWebExchange serverWebExchange = super.createExchange(request, response);

				final MultiValueMap<String, String> queryParams = request.getQueryParams();
				final String languageValue = queryParams.getFirst("lang");
				if (StringUtils.isEmpty(languageValue) == false) {
					LocaleContextHolder.setLocaleContext(() -> Locale.forLanguageTag(languageValue), true);
				} else {
					LocaleContext localeContext = serverWebExchange.getLocaleContext();
					if (localeContext != null) {
						LocaleContextHolder.setLocaleContext(localeContext, true);
					}
				}
				return serverWebExchange;
			}
		};
	}

}
