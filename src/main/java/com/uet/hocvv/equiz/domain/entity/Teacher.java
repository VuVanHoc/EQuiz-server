package com.uet.hocvv.equiz.domain.entity;

import com.uet.hocvv.equiz.domain.enu.GenderType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "teacher")
public class Teacher extends BaseEntity {
	
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private Date birthDay;
	private String workplace;
	private String gender;
	private String prefixJob; //GS, PGS.TS, TS, ThS, ...etc
	private List<String> experiences;
	
	
}
