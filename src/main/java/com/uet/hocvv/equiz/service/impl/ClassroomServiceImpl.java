package com.uet.hocvv.equiz.service.impl;

import com.uet.hocvv.equiz.common.CommonMessage;
import com.uet.hocvv.equiz.domain.entity.Classroom;
import com.uet.hocvv.equiz.domain.entity.Teacher;
import com.uet.hocvv.equiz.domain.entity.User;
import com.uet.hocvv.equiz.domain.request.CreateClassroomRequest;
import com.uet.hocvv.equiz.domain.request.Join2ClassroomRequest;
import com.uet.hocvv.equiz.domain.request.SearchDTO;
import com.uet.hocvv.equiz.domain.response.ClassroomDTO;
import com.uet.hocvv.equiz.domain.response.ResponseListDTO;
import com.uet.hocvv.equiz.repository.ClassroomRepository;
import com.uet.hocvv.equiz.repository.ClassroomStudentRepository;
import com.uet.hocvv.equiz.repository.TeacherRepository;
import com.uet.hocvv.equiz.repository.UserRepository;
import com.uet.hocvv.equiz.service.ClassroomService;
import com.uet.hocvv.equiz.utils.JwtAuthenticationFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClassroomServiceImpl implements ClassroomService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ClassroomRepository classroomRepository;
	@Autowired
	ClassroomStudentRepository classroomStudentRepository;
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	UserRepository userRepository;
	
	@Override
	public ResponseListDTO getList(int pageIndex, int pageSize, SearchDTO searchDTO) throws Exception {
		
		Sort sort = Sort.by("createdDate").descending();
		if (!searchDTO.getOrderBy().isEmpty()) {
			if (searchDTO.isOrderByAsc()) sort = Sort.by(searchDTO.getOrderBy()).ascending();
			else sort = Sort.by(searchDTO.getOrderBy()).descending();
		}
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		List<Classroom> classrooms = classroomRepository.findByResponsibleAndDeleted(searchDTO.getResponsibleId(), false, pageable);
		
		List<ClassroomDTO> classroomDTOS = new ArrayList<>();
		Teacher teacher = teacherRepository.findByUserId(searchDTO.getResponsibleId());
		User user = userRepository.findById(searchDTO.getResponsibleId()).orElse(new User());
		for (Classroom classroom : classrooms) {
			ClassroomDTO classroomDTO = modelMapper.map(classroom, ClassroomDTO.class);
			classroomDTO.setResponsiblePhone(teacher.getPhone());
			classroomDTO.setResponsibleName(user.getFullName());
			classroomDTO.setResponsibleEmail(teacher.getEmail());
			classroomDTO.setResponsibleAvatar(user.getAvatar());
			classroomDTOS.add(classroomDTO);
		}
		int total = classroomRepository.countByResponsibleAndDeleted(searchDTO.getResponsibleId(), false);
		return new ResponseListDTO(classroomDTOS, total);
	}
	
	@Override
	public ClassroomDTO createOrUpdate(HttpServletRequest httpServletRequest, CreateClassroomRequest createClassroomRequest) throws Exception {
		User currentUser = jwtAuthenticationFilter.getUserFromRequest(httpServletRequest);
		
		Classroom classroom;
		if (createClassroomRequest.getId() != null) {
//			handle update here
			Optional<Classroom> classroomOptional = classroomRepository.findById(createClassroomRequest.getId());
			if (!classroomOptional.isPresent()) {
				throw new Exception(CommonMessage.NOT_FOUND.name());
			}
			classroom = classroomOptional.get();
		} else {
//			handle create new classroom
			classroom = new Classroom();
			
			int RANDOM_CODE_LENGTH = 8;
			String randomCode = RandomStringUtils.random(RANDOM_CODE_LENGTH, true, true);
			classroom.setCode(randomCode);
			
		}
		classroom.setName(createClassroomRequest.getName());
		classroom.setDescription(createClassroomRequest.getDescription());
		classroom.setIsPrivate(!"PUBLIC".equals(createClassroomRequest.getClassroomType()));
		classroom.setPassword(createClassroomRequest.getPassword());
		classroom.setCreatedBy(currentUser.getId());
		classroom.setResponsible(currentUser.getId());
		
		classroomRepository.save(classroom);
		
		Teacher teacher = teacherRepository.findByUserId(currentUser.getId());
		
		ClassroomDTO classroomDTO = modelMapper.map(classroom, ClassroomDTO.class);
		if (teacher != null) {
			classroomDTO.setResponsibleEmail(teacher.getEmail());
			classroomDTO.setResponsibleId(teacher.getId());
			classroomDTO.setResponsibleName(currentUser.getFullName());
			classroomDTO.setResponsibleAvatar(currentUser.getAvatar());
			classroomDTO.setResponsiblePhone(teacher.getPhone());
		}
		return classroomDTO;
	}
	
	@Override
	public String delete(String id) throws Exception {
		Optional<Classroom> classroomOptional = classroomRepository.findById(id);
		if (!classroomOptional.isPresent()) {
			throw new Exception(CommonMessage.NOT_FOUND.name());
		}
		Classroom classroom = classroomOptional.get();
		classroom.setDeleted(true);
		classroom.setUpdatedDate(new Date());
		classroomRepository.save(classroom);
//		TODO: send email or notification to student in classroom
		return CommonMessage.SUCCESS.name();
	}
	
	@Override
	public ClassroomDTO getDetail(String id) throws Exception {
		Optional<Classroom> classroomOptional = classroomRepository.findById(id);
		if (!classroomOptional.isPresent()) {
			throw new Exception(CommonMessage.NOT_FOUND.name());
		}
		Classroom classroom = classroomOptional.get();
		
		ClassroomDTO classroomDTO = modelMapper.map(classroom, ClassroomDTO.class);
		classroomDTO.setCreatedDate(classroom.getCreatedDate().getTime());
		int totalStudent = classroomStudentRepository.countClassroomStudentByClassroomId(id);
		classroomDTO.setTotalStudent(totalStudent);
//		TODO: get responsible info here
		
		
		return classroomDTO;
	}
	
	@Override
	public String studentJoinToClassroom(Join2ClassroomRequest join2ClassroomRequest) throws Exception {
		return null;
	}
}
