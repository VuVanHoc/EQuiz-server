package com.uet.hocvv.equiz.domain.entity.word;

import com.uet.hocvv.equiz.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "dictionaries")
public class Dictionary extends BaseEntity {
	
	private String word;
	private String pronunciation;
	private List<WordDetail> wordDetailList;
}
