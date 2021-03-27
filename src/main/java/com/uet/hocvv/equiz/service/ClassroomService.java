package com.uet.hocvv.equiz.service;

import com.uet.hocvv.equiz.domain.request.CreateClassroomRequest;
import com.uet.hocvv.equiz.domain.request.Join2ClassroomRequest;
import com.uet.hocvv.equiz.domain.request.SearchDTO;
import com.uet.hocvv.equiz.domain.response.ClassroomDTO;
import com.uet.hocvv.equiz.domain.response.ResponseListDTO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface ClassroomService {
	
	ResponseListDTO getList(int pageIndex, int pageSize, SearchDTO searchDTO) throws Exception;
	
	ClassroomDTO createOrUpdate(HttpServletRequest httpServletRequest, CreateClassroomRequest createClassroomRequest) throws Exception;
	
	String delete(String id) throws Exception;
	
	ClassroomDTO getDetail(String id) throws Exception;
	
	String studentJoinToClassroom(Join2ClassroomRequest join2ClassroomRequest) throws Exception;
	
	ResponseListDTO getListClassroomForStudent(int pageIndex, int pageSize, SearchDTO searchDTO) throws Exception;
	
	
	
}
