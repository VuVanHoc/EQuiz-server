package com.uet.hocvv.equiz.controller;

import com.uet.hocvv.equiz.domain.RestBody;
import com.uet.hocvv.equiz.domain.request.CreateActivityRequest;
import com.uet.hocvv.equiz.domain.request.SaveResultPracticeRequest;
import com.uet.hocvv.equiz.domain.request.SearchDTO;
import com.uet.hocvv.equiz.domain.response.ActivityDTO;
import com.uet.hocvv.equiz.domain.response.ResponseListDTO;
import com.uet.hocvv.equiz.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/activity")
public class ActivityController {
	
	@Autowired
	ActivityService activityService;
	
	@PostMapping("getList")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> getListActivityForTeacher(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
	                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
	                                                   @RequestBody SearchDTO searchDTO)
			throws Exception {
		ResponseListDTO responseListDTO = activityService.getListActivityForTeacher(pageIndex, pageSize, searchDTO);
		RestBody restBody = RestBody.success(responseListDTO);
		return ResponseEntity.ok(restBody);
	}
	
	@PostMapping("create")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> createActivity(@RequestBody CreateActivityRequest createActivityRequest) throws Exception {
		ActivityDTO activityDTO = activityService.createOrUpdate(createActivityRequest);
		RestBody restBody = RestBody.success(activityDTO);
		return ResponseEntity.ok(restBody);
	}
	
	@PostMapping("update")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> updateActivity(@RequestBody CreateActivityRequest createActivityRequest) throws Exception {
		ActivityDTO activityDTO = activityService.createOrUpdate(createActivityRequest);
		RestBody restBody = RestBody.success(activityDTO);
		return ResponseEntity.ok(restBody);
	}
	
	@GetMapping("delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> deleteActivity(@PathVariable("id") String id) throws Exception {
		String result = activityService.delete(id);
		RestBody restBody = RestBody.success(result);
		return ResponseEntity.ok(restBody);
	}
	
	@PostMapping("saveResultPractice")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> saveResultPractice(@RequestBody SaveResultPracticeRequest saveResultPracticeRequest) throws Exception {
		String result = activityService.saveResultPractice(saveResultPracticeRequest);
		RestBody restBody = RestBody.success(result);
		return ResponseEntity.ok(restBody);
	}
	
	@PostMapping("getListHistoryPractice")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<Object> getListHistoryPractice(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
	                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
	                                                     @RequestBody SearchDTO searchDTO) throws Exception {
		ResponseListDTO responseListDTO = activityService.getHistoryPracticeForStudent(pageIndex, pageSize, searchDTO);
		RestBody restBody = RestBody.success(responseListDTO);
		return ResponseEntity.ok(restBody);
	}
	
	@GetMapping("getRandomCrossword")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getRandomCrossword(@RequestParam(value = "level") String level, @RequestParam(value = "subject") String subject) throws Exception {
		ActivityDTO activityDTO = activityService.getRandomCrosswordByLevelAndSubject(level, subject);
		RestBody restBody = RestBody.success(activityDTO);
		return ResponseEntity.ok(restBody);
	}
	
}
