package yoonho.demo.reactive.dto.product;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class ProductReq {
	private final static String SPLIT_CHAR = "$";
	private String spdNo;
	private String sitmNo;
	private String lrtrNo;
	private Integer odQty;
	
	@JsonIgnore
	public static ProductReq getProductReqByGroupedKey(String groupedKey) {
		if(StringUtils.isEmpty(groupedKey)) return new ProductReq();
		String[] groupedKeys = groupedKey.split("\\".concat(SPLIT_CHAR));
		if(groupedKeys.length != 3) return new ProductReq();
		
		return new ProductReq(groupedKeys[0],groupedKeys[1], groupedKeys[2], 0);
		
	}
}
