package com.uet.hocvv.equiz.domain.entity;

import com.uet.hocvv.equiz.domain.enu.GenderType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "student")
public class Student extends BaseEntity {
	
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String address;
	private Date birthday;
	private String gender;
}
