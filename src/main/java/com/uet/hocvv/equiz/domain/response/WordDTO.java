package com.uet.hocvv.equiz.domain.response;

import lombok.Data;

@Data
public class WordDTO {
	
	private String value;
	private String meaning;
	private String pronunciation;
	private String valueFromWordAPI;
}
