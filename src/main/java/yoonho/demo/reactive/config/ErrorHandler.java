package yoonho.demo.reactive.config;

import java.io.IOException;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;
import yoonho.demo.reactive.dto.CommonRes;

@Configuration
@Order(-2)
public class ErrorHandler implements ErrorWebExceptionHandler  {
	private ObjectMapper objectMapper;

	  public ErrorHandler(ObjectMapper objectMapper) {
	    this.objectMapper = objectMapper;
	  }

	  @Override
	  public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {

			CommonRes<Void> commonRes = new CommonRes<Void>();
			commonRes.setReturnCode("500");
			commonRes.setMessage(throwable.getMessage());

			DataBufferFactory bufferFactory = serverWebExchange.getResponse().bufferFactory();
			serverWebExchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			DataBuffer dataBuffer = null;
			try {
				dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(commonRes));
			} catch (JsonProcessingException e) {
				dataBuffer = bufferFactory.wrap("".getBytes());
			}
			serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
			return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer));
//	      
//	    
//	    if (throwable instanceof IOException) {
//	      serverWebExchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
//	      DataBuffer dataBuffer = null;
//	      try {
//	        dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(commonRes));
//	      } catch (JsonProcessingException e) {
//	        dataBuffer = bufferFactory.wrap("".getBytes());
//	      }
//	      serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
//	      return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer));
//	    }
//
//	    serverWebExchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
//	    serverWebExchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
//	    DataBuffer dataBuffer = bufferFactory.wrap("Unknown error".getBytes());
//	    return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer));
	  }

	  public class HttpError {

	    private String message;

	    HttpError(String message) {
	      this.message = message;
	    }

	    public String getMessage() {
	      return message;
	    }
	  }
}
