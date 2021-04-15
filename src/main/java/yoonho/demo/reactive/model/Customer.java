package yoonho.demo.reactive.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Persistable<Long> {

	@Id
	@Column 
	@With
	private Long id;
	
	@Column
	private String name;
	
	@Column
	@CreatedBy
	private String regrId;
	
	@CreatedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime regDttm;
	
	@LastModifiedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
