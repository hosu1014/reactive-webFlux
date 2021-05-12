package yoonho.demo.reactive.dto;

import lombok.Data;
import yoonho.demo.reactive.base.masking.Masking;
import yoonho.demo.reactive.base.masking.MaskingType;

@Data
public class UserAssetDtoResponse {
	private String userId;
	private String username;
	@Masking(type=MaskingType.CARD_NO)
	private String ccrdNo;
	@Masking(type=MaskingType.ACCOUT_NO)
	private String actnNo;
}
