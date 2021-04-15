package yoonho.demo.reactive.dto.product;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Slf4j
public class ProductReq {
	public final static String SPLIT_CHAR = "$"; 
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
	
	@JsonIgnore
	public String getGroupedKey() {
		return this.getSpdNo()
				.concat(SPLIT_CHAR)
				.concat(this.getSitmNo())
				.concat(SPLIT_CHAR)
				.concat(this.getLrtrNo());
	}
}
