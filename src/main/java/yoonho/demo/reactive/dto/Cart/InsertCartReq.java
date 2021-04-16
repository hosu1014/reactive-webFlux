package yoonho.demo.reactive.dto.Cart;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data	
@AllArgsConstructor
@NoArgsConstructor
public class InsertCartReq {
	private String spdNo;
	private String sitmNo;
	private Integer odQty;
	
	@JsonIgnore
	public String getGroupedKey() {
		return this.getSpdNo()
				.concat(this.getSitmNo());
	}
	
}
