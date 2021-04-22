package yoonho.demo.reactive.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

@Data
@Table("om_cart")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Persistable<Long>{
	@Transient
	private final static String SPLIT_CHAR = "$";
	@Id
	@Column("cart_sn")
	@With
	private Long id;
	
	private String trNo;
	private String lrtrNo;
	
	@CreatedBy
	private String mbNo;
	private String spdNo;
	@Column("sitm_no")
	private String sitmNo;
	private Integer odQty;
	
	@CreatedDate
	private LocalDateTime regDttm;	
	@LastModifiedDate
	private LocalDateTime modDttm;
	
	@Transient
	private boolean newFlag;
	
	@JsonIgnore
	public String getGroupedKey() {
		return this.getSpdNo()
				.concat(SPLIT_CHAR)
				.concat(this.getSitmNo())
				.concat(SPLIT_CHAR)
				.concat(this.getLrtrNo());
	}
	
	@Override
	public boolean isNew() {
		return this.newFlag;
	}
	
}
