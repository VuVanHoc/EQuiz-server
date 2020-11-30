package com.uet.hocvv.equiz.domain.entity;

import com.uet.hocvv.equiz.domain.enu.Gender;
import com.uet.hocvv.equiz.domain.enu.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
	
	private String username;
	private String password;
	private String email;
	private Date birthday;
	private String firstName;
	private String lastName;
	private UserType userType;
	private Gender gender;
	private Boolean active;
	
	
}
