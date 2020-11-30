package com.uet.hocvv.equiz.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
public abstract class BaseEntity {
	
	@Id
	private String id;
	@CreatedDate
	private Date createdDate;
	@LastModifiedDate
	private Date updatedDate;
	private boolean deleted = false;
	
	public BaseEntity() {
		this.createdDate = new Date();
		this.updatedDate = new Date();
	}
	
}
