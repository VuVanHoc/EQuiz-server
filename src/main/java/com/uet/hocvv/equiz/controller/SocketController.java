package com.uet.hocvv.equiz.controller;

import com.uet.hocvv.equiz.domain.request.MessageBean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SocketController {
//	@MessageMapping("/user-all")
//	@SendTo("/notification/classroom/{id}")
//	public MessageBean send(@Payload MessageBean message, @PathVariable("id") String classroomId) {
//		return message;
//	}
	
	
}
