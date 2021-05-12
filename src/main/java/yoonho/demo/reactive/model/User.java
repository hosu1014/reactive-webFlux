package yoonho.demo.reactive.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import yoonho.demo.reactive.auth.Role;
import yoonho.demo.reactive.base.dataencrypt.DataEncrypt;
import yoonho.demo.reactive.base.dataencrypt.EncryptType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("me_member")
public class User implements UserDetails, Persistable<Long>{
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column("mb_no")
	@With
	private Long Id;
	private String userId;
	@Transient
	private String password;
	@Column("passwd")
	@JsonIgnore
	private String encPassword;
	@Column("mb_nm")
	private String username;
	private String email;
	private String rtGrpNo;
	@DataEncrypt(type=EncryptType.CARD_NO)
	private String ccrdNo;
	@DataEncrypt(type=EncryptType.ACCOUT_NO)
	private String actnNo;
	@CreatedBy
	private String regrId;
	@CreatedDate
	private LocalDateTime regDttm;
	@LastModifiedBy
	private String modrId;
	@LastModifiedDate
	private LocalDateTime modDttm;
	

	@Transient
	private boolean newFlag;
	@Transient
	private Boolean enabled;
	@Transient
	private List<Role> roles;
	
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(authority-> new SimpleGrantedAuthority(authority.name()) )
				.collect(Collectors.toList());
	}
	
	
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}
	@Override
	public boolean isAccountNonLocked() {
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	@Override
	public boolean isNew() {
		return this.newFlag;
	}
}
