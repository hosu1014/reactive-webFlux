package yoonho.demo.reactive.service.external;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import yoonho.demo.reactive.dto.CommonRes;
import yoonho.demo.reactive.dto.product.Item;
import yoonho.demo.reactive.dto.product.ProductReq;
import yoonho.demo.reactive.service.ApiService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductApi extends ApiService {
	@Value("${api.baseUri.lotteon}")
	private String baseUrl;
	
	public Flux<Item> getProductList(List<ProductReq> productReqList) {
		return callPost(baseUrl, "/product/v1/detail/productDetailList?dataType={dataType}", new Object[]{"LIGHT2"}, productReqList)
		        .bodyToMono(new ParameterizedTypeReference<CommonRes<List<Item>>>() {})
				.map(p -> {
					return p.getData();
				})
				.flatMapMany(Flux::fromIterable);
	}
	
	public Flux<Item> getProduct(ProductReq productReq) {
		List<ProductReq> productReqList = new ArrayList<ProductReq>();
		productReqList.add(productReq);
		
		return getProductList(productReqList);
	}

}
