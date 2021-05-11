package com.uet.hocvv.equiz.service;

import com.uet.hocvv.equiz.domain.request.*;
import com.uet.hocvv.equiz.domain.response.ActivityDTO;
import com.uet.hocvv.equiz.domain.response.ResponseListDTO;
import org.springframework.stereotype.Service;

@Service
public interface ActivityService {
	
	ResponseListDTO getListActivityForTeacher(int pageIndex, int pageSize, SearchDTO searchDTO) throws Exception;
	
	ActivityDTO createOrUpdate(CreateActivityRequest createActivityRequest) throws Exception;
	
	String delete(String id) throws Exception;
	
	String saveResultPractice(SaveResultPracticeRequest saveResultPracticeRequest) throws Exception;
	
	ResponseListDTO getHistoryPracticeForStudent(int pageIndex, int pageSize, SearchDTO searchDTO) throws Exception;
	
	ActivityDTO getRandomCrosswordByLevelAndSubject(String level, String subject) throws Exception;
	
	String shareActivity(ShareActivityRequest shareActivityRequest) throws Exception;
	
	String assignForClassroom(AssignActivityRequest assignActivityRequest) throws Exception;
	
	ResponseListDTO getActivitiesForClassroom(SearchClassroomActivityRequest searchClassroomActivityRequest,
	                                          int pageIndex, int pageSize);
	
	ActivityDTO getDetail(String id) throws Exception;
	
	String updateDeadlineActivity(String id, Long endTime) throws Exception;
	
	String deleteClassroomActivity(String id) throws Exception;
}
