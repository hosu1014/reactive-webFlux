package yoonho.demo.reactive.service;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiService {
	@Autowired private WebClient webClient;
	
	protected <T> WebClient.ResponseSpec callPost(String baseUrl, String uri, Object[] params, T requestObject) {
		return webClient.mutate()
		.baseUrl(baseUrl)
		.build()
		.post()
		.uri(uri, params)
		.accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestObject)
        .retrieve()
        .onStatus(status -> status.is4xxClientError() 
                || status.is5xxServerError()
                , clientResponse ->
                 clientResponse.bodyToMono(String.class)
                 .map(body -> new RuntimeException(body)));
	}
	
	protected <T> WebClient.ResponseSpec callGet(HttpHeaders httpHeaders, String baseUrl, String uri, MultiValueMap<String, String> queryParam) {
		return webClient.mutate()
		.baseUrl(baseUrl)
		.build()
		.get()
        .uri(uriBuilder -> uriBuilder.path(uri).queryParams(queryParam).build())
        .headers(headers -> {
            if (ObjectUtils.isEmpty(httpHeaders) == false) {
                headers.setAll(httpHeaders.toSingleValueMap());
            }
        })
        .accept(MediaType.APPLICATION_JSON, APPLICATION_FORM_URLENCODED)
        .retrieve()
        .onStatus(status -> status.is4xxClientError() 
                || status.is5xxServerError()
                , clientResponse ->
                 clientResponse.bodyToMono(String.class)
                 .map(body -> new RuntimeException(body)));
	}
	
	
}
