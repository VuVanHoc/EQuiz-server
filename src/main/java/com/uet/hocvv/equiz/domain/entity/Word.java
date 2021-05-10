package com.uet.hocvv.equiz.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document("words")
@AllArgsConstructor
@NoArgsConstructor
public class Word extends BaseEntity{
	
	private String value;
	private String meaning;
	private String pronunciation;
	private List<String> examples;
	private String dataFromWordApi;
	
	public Word(String value) {
		this.value = value;
	}
}
