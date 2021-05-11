package yoonho.demo.reactive.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import yoonho.demo.reactive.base.dataencrypt.DataEncrypt;
import yoonho.demo.reactive.base.dataencrypt.EncryptType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity implements Persistable<Long> {

	@Id
	@With
	private Long id;
	private String name;
	@DataEncrypt(type=EncryptType.CARD_NO)
	private String ccrdNo;
	@CreatedBy
	private String regrId;
	@CreatedDate
	private LocalDateTime regDttm;
	@LastModifiedDate
	private LocalDateTime modDttm;
	
	
	@Transient
    private boolean newFlag;
	
	@Override
	public boolean isNew() {
		return newFlag || this.id == null;
	}
	
	public void setNewFlag(boolean flag) {
		this.newFlag = flag;
	}
	
	public Customer(Long id, String name, boolean newFlag) {
		this.id = id;
		this.name = name;
		this.setNewFlag(newFlag);
	}
}
