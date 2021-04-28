package yoonho.demo.reactive.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorHandler extends AbstractErrorWebExceptionHandler {
	

	public ErrorHandler(ErrorAttributes errorAttributes,
			org.springframework.boot.autoconfigure.web.WebProperties.Resources resources,
			ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
		super(errorAttributes, resources, applicationContext);
		this.setMessageWriters(configurer.getWriters());
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}
	
	 private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
		Map<String, Object> errorAttributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
		Throwable throwable = getError(request);
		
		
//		boolean isCustomException = throwable instanceof CustomException;
		int status = (int) errorAttributes.get("status");
		
		errorAttributes.put("exception", throwable.getClass().getSimpleName());
		errorAttributes.put("message", throwable.getMessage());
		
		return ServerResponse.status(status).contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(errorAttributes));
	}

}
