package com.uet.hocvv.equiz.domain.entity;

import com.uet.hocvv.equiz.domain.enu.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
	
	private String username;
	private String password;
	private String fullName;
	private UserType userType;
	private Boolean active = false;
	private String defaultColor;
	private String avatar;
	
	
}
