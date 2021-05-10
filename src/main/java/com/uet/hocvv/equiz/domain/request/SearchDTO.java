package com.uet.hocvv.equiz.domain.request;

import lombok.Data;

@Data
public class SearchDTO {
	
	private String orderBy;
	private String searchText;
	private String userId;
	private String responsibleId;
	private boolean orderByAsc;
	
}
