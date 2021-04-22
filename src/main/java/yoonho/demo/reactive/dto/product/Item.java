package yoonho.demo.reactive.dto.product;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Item {
	private String spdNo;
	private String spdNm;
	private String sitmNo;
	private String sitmNm;
	private String trGrpCd;
	private String trNo;
	private String lrtrNo;
	private String brdCd;
	private String brdNm;
	private String slStatCd;
	private Long slPrc;
	private String estmtDlvTxt;
	private String returnCode;
	@JsonFormat(pattern = "yyyyMMddHHmmss")
	private LocalDateTime slStrtDttm;
	@JsonFormat(pattern = "yyyyMMddHHmmss")
	private LocalDateTime slEndDttm;
	
	@JsonProperty(value = "saleStatCode")
	public StatCode getSaleStatCode() {
		LocalDateTime now = LocalDateTime.now();
		
		return StatCode.lookup(this.getSlStatCd()).equals(StatCode.SALE) 
				&& (now.isAfter(this.getSlStrtDttm()) && now.isBefore(this.getSlEndDttm())) 
				? StatCode.SALE: StatCode.SOUT; 
	}
	
	public enum StatCode {
		SALE("판매중"),
		SOUT("품절");
		
		private String nm;
		
		StatCode(String nm) {
			this.nm = nm;
		}
		
		public static StatCode lookup(String code) {
			return Arrays.stream(values())
					.filter(stat -> stat.valueOf(code).equals(stat))
					.findFirst()
					.orElse(StatCode.SOUT);
		}
	}
}
