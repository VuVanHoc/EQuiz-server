package com.uet.hocvv.equiz.domain.entity.word;

import lombok.Data;

import java.util.List;

@Data
public class WordDetail {
	
	private String type;
	private String meaning;
	private List<String> examples;
	
}
