package com.uet.hocvv.equiz.controller;

import com.uet.hocvv.equiz.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RedirectController {
	
	@Autowired
	UserServiceImpl userService;
	
	@GetMapping("verifyEmail")
	public String verifyEmail(@RequestParam(name = "id") String id) throws Exception {
		userService.verifyAccount(id);
		return "redirect:http://www.google.com";
	}
}
