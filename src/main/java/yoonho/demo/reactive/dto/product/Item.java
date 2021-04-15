package yoonho.demo.reactive.dto.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
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
}
