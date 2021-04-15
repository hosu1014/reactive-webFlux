package yoonho.demo.reactive.dto.Cart;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InsertCartReq {
	public final static String SPLIT_CHAR = "$";
	private String spdNo;
	private String sitmNo;
	private Integer odQty;
	
	@JsonIgnore
	public String getDistinctKey() {
		return this.getSpdNo()
				.concat(SPLIT_CHAR)
				.concat(this.getSitmNo());
	}
}
