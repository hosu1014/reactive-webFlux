package yoonho.demo.reactive.exapi.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import yoonho.demo.reactive.dto.CommonRes;
import yoonho.demo.reactive.dto.product.Item;
import yoonho.demo.reactive.dto.product.ProductReq;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductApi {
	private final WebClient webclient;
	
	@Value("${api.baseUri.lotteon}")
	private String baseUri;
	
	public Flux<Item> getProductList(List<ProductReq> productReqList) {
		return webclient.mutate()
				.baseUrl(baseUri)
				.build()
				.post()
				.uri("/product/v1/detail/productDetailList?dataType={dataType}", new Object[]{"LIGHT2"})
				.accept(MediaType.APPLICATION_JSON_UTF8)
		        .contentType(MediaType.APPLICATION_JSON_UTF8)
		        .bodyValue(productReqList)
		        .retrieve()
		        .onStatus(status -> status.is4xxClientError() 
                        || status.is5xxServerError()
                        , clientResponse ->
                         clientResponse.bodyToMono(String.class)
                         .map(body -> new RuntimeException(body)))
		        .bodyToMono(new ParameterizedTypeReference<CommonRes<List<Item>>>() {})
				.map(p -> {
					return p.getData();
				})
				.flatMapMany(Flux::fromIterable);
		        
		
	}
	
	public Flux<Item> getProduct(ProductReq productReq) {
		List<ProductReq> productReqList = new ArrayList<ProductReq>();
		productReqList.add(productReq);
		
		return webclient.mutate()
				.baseUrl(baseUri)
				.build()
				.post()
				.uri("/product/v1/detail/productDetailList?dataType={dataType}", new Object[]{"LIGHT2"})
				.accept(MediaType.APPLICATION_JSON_UTF8)
		        .contentType(MediaType.APPLICATION_JSON_UTF8)
		        .bodyValue(productReqList)
		        .retrieve()
		        .onStatus(status -> status.is4xxClientError() 
                        || status.is5xxServerError()
                        , clientResponse ->
                         clientResponse.bodyToMono(String.class)
                         .map(body -> new RuntimeException(body)))
		        .bodyToMono(new ParameterizedTypeReference<CommonRes<List<Item>>>() {})
				.map(p -> {
					return p.getData();
				})
				.flatMapMany(Flux::fromIterable);
		        
		
	}

}
