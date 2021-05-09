package com.uet.hocvv.equiz.domain.response;

import com.uet.hocvv.equiz.domain.entity.word.MeaningDetail;
import com.uet.hocvv.equiz.domain.entity.word.WordDetail;
import lombok.Data;

import java.util.List;

@Data
public class WordDTO {
	
	private String value;
	private String meaning;
	private String pronunciation;
	private List<WordDetail> detailList;
	
}
