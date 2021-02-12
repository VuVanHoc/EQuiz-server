package com.uet.hocvv.equiz.controller;

import com.uet.hocvv.equiz.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RedirectController {
	@Value("${web.url}")
	String webReactUrl;
	
	@Autowired
	UserServiceImpl userService;
	
	@GetMapping("verifyEmail")
	public String verifyEmail(@RequestParam(name = "id") String id) throws Exception {
		userService.verifyAccount(id);
		return "redirect:" + webReactUrl;
	}
	
	@GetMapping("verifyForgotPassword")
	public String verifyForgotPassword(@RequestParam(name = "email") String email,
	                                   @RequestParam(name = "pass") String pass,
	                                   @RequestParam("expriedTime") Long expriedTime) throws Exception {
		userService.verifyForgotPassword(email, pass, expriedTime);
		return "redirect:" + webReactUrl;
	}
}
