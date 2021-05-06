package com.uet.hocvv.equiz.service.impl;

import com.uet.hocvv.equiz.common.CommonMessage;
import com.uet.hocvv.equiz.domain.entity.*;
import com.uet.hocvv.equiz.domain.enu.ActivityType;
import com.uet.hocvv.equiz.domain.enu.LevelType;
import com.uet.hocvv.equiz.domain.enu.UserType;
import com.uet.hocvv.equiz.domain.request.*;
import com.uet.hocvv.equiz.domain.response.ActivityDTO;
import com.uet.hocvv.equiz.domain.response.ResponseListDTO;
import com.uet.hocvv.equiz.repository.*;
import com.uet.hocvv.equiz.service.ActivityService;
import com.uet.hocvv.equiz.utils.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActivityServiceImpl implements ActivityService {
	@Autowired
	ActivityRepository activityRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	StudentActivityRepository studentActivityRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Value("${default.md5.hash.password}")
	private String defaultMd5HashPassword;
	@Autowired
	ClassroomActivityRepository classroomActivityRepository;
	
	@Override
	public ResponseListDTO getListActivityForTeacher(int pageIndex, int pageSize, SearchDTO searchDTO) throws Exception {
		Sort sort = Sort.by("createdDate").descending();
		if (!searchDTO.getOrderBy().isEmpty()) {
			if (searchDTO.isOrderByAsc()) sort = Sort.by(searchDTO.getOrderBy()).ascending();
			else sort = Sort.by(searchDTO.getOrderBy()).descending();
		}
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		List<Activity> activities = activityRepository.findByOwnerIdAndDeletedIsFalse(searchDTO.getUserId(), pageable);
		List<ActivityDTO> activityDTOS = new ArrayList<>();
		
		User user = userRepository.findById(searchDTO.getResponsibleId()).orElse(new User());
		Teacher teacher = teacherRepository.findByUserId(user.getId());
		
		activities.forEach(activity -> {
			ActivityDTO activityDTO = new ActivityDTO();
			activityDTO.setId(activity.getId());
			activityDTO.setName(activity.getName());
			activityDTO.setType(activity.getType().name());
			activityDTO.setLevel(activity.getLevel().name());
			activityDTO.setCreatedDate(activity.getCreatedDate().getTime());
			activityDTO.setSharePublic(activity.isSharePublic());
			activityDTO.setDataSetup(activity.getDataSetup());
			activityDTO.setDescription(activity.getDescription());
			activityDTO.setResponsibleName(user.getFullName());
			activityDTO.setResponsibleAvatar(user.getAvatar());
			if (teacher != null) {
				activityDTO.setResponsibleEmail(teacher.getEmail());
				activityDTO.setResponsiblePhone(teacher.getPhone());
			}
			activityDTOS.add(activityDTO);
		});
		int total = activityRepository.countByOwnerIdAndDeletedIsFalse(searchDTO.getUserId());
		
		return new ResponseListDTO(activityDTOS, total);
	}
	
	@Override
	public ActivityDTO createOrUpdate(CreateActivityRequest createActivityRequest) throws Exception {
		Activity activity;
		if (createActivityRequest.getId() == null) {
			activity = new Activity();
			activity.setCreatedBy(createActivityRequest.getResponsibleId());
			
		} else {
			Optional<Activity> optionalActivity = activityRepository.findById(createActivityRequest.getId());
			if (optionalActivity.isPresent()) {
				activity = optionalActivity.get();
			} else {
				throw new Exception(CommonMessage.NOT_FOUND.name());
			}
		}
		activity.setDataSetup(createActivityRequest.getDataSetup());
		activity.setLevel(LevelType.valueOf(createActivityRequest.getLevel()));
		activity.setName(createActivityRequest.getName());
		activity.setType(ActivityType.valueOf(createActivityRequest.getType()));
		activity.setOwnerId(createActivityRequest.getResponsibleId());
		activity.setDescription(createActivityRequest.getDescription());
		activity.setSubject(createActivityRequest.getSubject());
		activityRepository.save(activity);
		return modelMapper.map(activity, ActivityDTO.class);
		
	}
	
	@Override
	public String delete(String id) throws Exception {
		
		Optional<Activity> activityOptional = activityRepository.findById(id);
		if (!activityOptional.isPresent()) throw new Exception(CommonMessage.NOT_FOUND.name());
		activityOptional.get().setDeleted(true);
		activityRepository.save(activityOptional.get());
		return CommonMessage.SUCCESS.name();
	}
	
	@Override
	public String saveResultPractice(SaveResultPracticeRequest saveResultPracticeRequest) throws Exception {
		StudentActivity studentActivity = new StudentActivity();
		
		if (saveResultPracticeRequest.getActivityId() == null) {
			Activity activity = new Activity();
			activity.setDataSetup(saveResultPracticeRequest.getDataSetup());
			activity.setType(ActivityType.valueOf(saveResultPracticeRequest.getActivityType()));
			activity.setLevel(LevelType.valueOf(saveResultPracticeRequest.getLevel()));
			activity.setSharePublic(true);
			activityRepository.save(activity);
			studentActivity.setActivityId(activity.getId());
		} else {
			studentActivity.setActivityId(saveResultPracticeRequest.getActivityId());
		}
		studentActivity.setStudentId(saveResultPracticeRequest.getStudentId());
		studentActivity.setStartTime(saveResultPracticeRequest.getStartTime());
		studentActivity.setEndTime(saveResultPracticeRequest.getEndTime());
		studentActivity.setScore(saveResultPracticeRequest.getTotalQuestion() == 0 ? 0 : saveResultPracticeRequest.getTotalAnswerCorrect() * 1.0 / saveResultPracticeRequest.getTotalQuestion());
		studentActivity.setTotalAnswerCorrect(saveResultPracticeRequest.getTotalAnswerCorrect());
		studentActivity.setTotalQuestion(saveResultPracticeRequest.getTotalQuestion());
		studentActivityRepository.save(studentActivity);
		return CommonMessage.SUCCESS.name();
	}
	
	@Override
	public ResponseListDTO getHistoryPracticeForStudent(int pageIndex, int pageSize, SearchDTO searchDTO) throws Exception {
		Sort sort = Sort.by("createdDate").descending();
		if (!searchDTO.getOrderBy().isEmpty()) {
			if (searchDTO.isOrderByAsc()) sort = Sort.by(searchDTO.getOrderBy()).ascending();
			else sort = Sort.by(searchDTO.getOrderBy()).descending();
		}
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		List<StudentActivity> studentActivityList = studentActivityRepository.findByStudentId(searchDTO.getUserId(), pageable);
		int totalResult = studentActivityRepository.countByStudentId(searchDTO.getUserId());
		List<ActivityDTO> activityDTOS = new ArrayList<>();
		studentActivityList.forEach(studentActivity -> {
			Activity activity = activityRepository.findById(studentActivity.getActivityId()).orElse(new Activity());
			ActivityDTO activityDTO = new ActivityDTO();
			activityDTO.setId(activity.getId());
			activityDTO.setHistoryActivityId(studentActivity.getId());
			activityDTO.setName(activity.getName());
			activityDTO.setType(activity.getType().name());
			activityDTO.setLevel(activity.getLevel().name());
			activityDTO.setStartTime(studentActivity.getStartTime());
			activityDTO.setEndTime(studentActivity.getEndTime());
			activityDTO.setScore(studentActivity.getScore());
			activityDTO.setTotalAnswerCorrect(studentActivity.getTotalAnswerCorrect());
			activityDTO.setTotalQuestion(studentActivity.getTotalQuestion());
			activityDTOS.add(activityDTO);
		});
		return new ResponseListDTO(activityDTOS, totalResult);
	}
	
	@Override
	public ActivityDTO getRandomCrosswordByLevelAndSubject(String level, String subject) throws Exception {
		SampleOperation sampleOperation = Aggregation.sample(3);
		Aggregation aggregation;
		
		if ("ALL".equals(subject)) {
			aggregation = Aggregation.newAggregation(sampleOperation,
					Aggregation.match(Criteria.where("level").is(level)
							.and("type").is(ActivityType.MATRIX_WORD.name())
							.and("sharePublic").is(true)));
		} else {
			aggregation = Aggregation.newAggregation(sampleOperation,
					Aggregation.match(Criteria.where("level").is(level)
							.and("subject").is(subject)
							.and("type").is(ActivityType.MATRIX_WORD.name())
							.and("sharePublic").is(true)));
		}
		AggregationResults<Activity> aggregationResults = mongoTemplate.aggregate(aggregation, "activity", Activity.class);
		List<Activity> activityList = aggregationResults.getMappedResults();
		if (activityList.isEmpty()) {
			throw new Exception(CommonMessage.NOT_FOUND.name());
		}
		return modelMapper.map(activityList.get(0), ActivityDTO.class);
	}
	
	@Override
	public String shareActivity(ShareActivityRequest shareActivityRequest) throws Exception {
		Activity activity;
		Optional<Activity> activityOptional = activityRepository.findById(shareActivityRequest.getActivityId());
		if (!activityOptional.isPresent()) {
			throw new Exception(CommonMessage.NOT_FOUND.name());
		}
		activity = activityOptional.get();
		if ("PUBLIC".equals(shareActivityRequest.getType())) {
			activity.setSharePublic(true);
			activity.setUpdatedDate(new Date());
			activityRepository.save(activity);
		} else {
			shareActivityForPerson(activity, shareActivityRequest.getEmail());
		}
		return CommonMessage.SUCCESS.name();
	}
	
	@Override
	public String assignForClassroom(AssignActivityRequest assignActivityRequest) {
		List<ClassroomActivity> classroomActivities = new ArrayList<>();
		if (assignActivityRequest.getClassroomIds() == null) {
			return CommonMessage.SUCCESS.name();
		}
		for (String classroomId : assignActivityRequest.getClassroomIds()) {
			ClassroomActivity classroomActivity = new ClassroomActivity();
			classroomActivity.setClassroomId(classroomId);
			classroomActivity.setActivityId(assignActivityRequest.getActivityId());
			classroomActivity.setEndTime(assignActivityRequest.getEndTime());
			classroomActivities.add(classroomActivity);
		}
		classroomActivityRepository.saveAll(classroomActivities);
//		TODO: put notification for student in each classroom here
		return CommonMessage.SUCCESS.name();
	}
	
	@Override
	public ResponseListDTO getActivitiesForClassroom(SearchClassroomActivityRequest searchClassroomActivityRequest,
	                                                 int pageIndex, int pageSize) {
		Sort sort = Sort.by("endTime").descending();
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		List<ActivityDTO> activityDTOS = new ArrayList<>();
		List<ClassroomActivity> classroomActivities = classroomActivityRepository.
				findByClassroomIdAndDeletedIsFalse(searchClassroomActivityRequest.getClassroomId(), pageable);
		
		if (classroomActivities == null) {
			return new ResponseListDTO(activityDTOS, 0);
		}
		
		for (ClassroomActivity classroomActivity : classroomActivities) {
			Activity activity = activityRepository.findById(classroomActivity.getActivityId()).orElse(new Activity());
			ActivityDTO activityDTO = modelMapper.map(activity, ActivityDTO.class);
			activityDTO.setEndTime(classroomActivity.getEndTime());
			activityDTO.setClassroomActivityId(classroomActivity.getId());
			activityDTOS.add(activityDTO);
		}
		int total = classroomActivityRepository.countByClassroomIdAndDeletedIsFalse(searchClassroomActivityRequest.getClassroomId());
		return new ResponseListDTO(activityDTOS, total);
	}
	
	@Override
	public ActivityDTO getDetail(String id) throws Exception {
		Optional<Activity> activityOptional = activityRepository.findById(id);
		if(!activityOptional.isPresent()) {
			throw new Exception(CommonMessage.NOT_FOUND.name());
		}
		Activity activity = activityOptional.get();
		ActivityDTO activityDTO = modelMapper.map(activity, ActivityDTO.class);
		activityDTO.setType(activity.getType().name());
		return activityDTO;
	}
	
	
	private void shareActivityForPerson(Activity activity, String email) {
		User user = userRepository.findByUsername(email);
		if (user == null) {
			user = new User();
			user.setUserType(UserType.TEACHER);
			user.setPassword(passwordEncoder.encode(defaultMd5HashPassword));
			user.setDefaultColor(generateRandomColor());
			user.setFullName(email);
			user.setUsername(email);
			userRepository.save(user);
			
			Teacher teacher = new Teacher();
			teacher.setUserId(user.getId());
			teacherRepository.save(teacher);
		}
		if (user.getUserType().equals(UserType.STUDENT)) {
			return;
		}
		Activity newActivity = modelMapper.map(activity, Activity.class);
		newActivity.setId(null);
		newActivity.setOwnerId(user.getId());
//		TODO: createdBy should be id of user who shared this activity
		newActivity.setCreatedBy(user.getId());
		
		activityRepository.save(newActivity);
	}
	
	private String generateRandomColor() {
		int random = new Random().nextInt(Constants.COLOR_TYPE.length);
		return Constants.COLOR_TYPE[random];
	}
	
}
