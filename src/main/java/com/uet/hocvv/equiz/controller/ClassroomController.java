package com.uet.hocvv.equiz.controller;

import com.uet.hocvv.equiz.domain.RestBody;
import com.uet.hocvv.equiz.domain.request.CreateClassroomRequest;
import com.uet.hocvv.equiz.domain.request.SearchDTO;
import com.uet.hocvv.equiz.domain.response.ClassroomDTO;
import com.uet.hocvv.equiz.domain.response.ResponseListDTO;
import com.uet.hocvv.equiz.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/classroom")
public class ClassroomController {
	
	@Autowired
	ClassroomService classroomService;
	
	@PostMapping(value = "getList", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> getList(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
	                                 @RequestParam(value = "pageSize", defaultValue = "15") int pageSize,
	                                 @RequestBody SearchDTO searchDTO) throws Exception {
		ResponseListDTO classroomList = classroomService.getList(pageIndex, pageSize, searchDTO);
		RestBody restBody = RestBody.success(classroomList);
		return ResponseEntity.ok(restBody);
	}
	
	@PostMapping(value = "create", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody CreateClassroomRequest createClassroomRequest, HttpServletRequest httpServletRequest) throws Exception {
		ClassroomDTO classroom = classroomService.createOrUpdate(httpServletRequest, createClassroomRequest);
		RestBody restBody = RestBody.success(classroom);
		return ResponseEntity.ok(restBody);
	}
	
	@PutMapping(value = "update", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody CreateClassroomRequest createClassroomRequest, HttpServletRequest httpServletRequest) throws Exception {
		ClassroomDTO classroom = classroomService.createOrUpdate(httpServletRequest, createClassroomRequest);
		RestBody restBody = RestBody.success(classroom);
		return ResponseEntity.ok(restBody);
	}
	
	@DeleteMapping(value = "delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable("id") String id) throws Exception {
		System.out.println(id);
		String result = classroomService.delete(id);
		RestBody restBody = RestBody.success(result);
		return ResponseEntity.ok(restBody);
	}
	
	@GetMapping(value = "detail/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> getDetail(@PathVariable("id") String id) throws Exception {
		ClassroomDTO classroomDTO = classroomService.getDetail(id);
		RestBody restBody = RestBody.success(classroomDTO);
		return ResponseEntity.ok(restBody);
	}
}
