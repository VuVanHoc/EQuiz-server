package com.uet.hocvv.equiz.domain.request;

import lombok.Data;

@Data
public class SearchQuestionDTO {
	
	private String ownerId;
	private String orderBy;
	private String searchText;
	private boolean orderByAsc;
	private String userId;
}
