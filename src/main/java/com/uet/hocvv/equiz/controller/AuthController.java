package com.uet.hocvv.equiz.controller;

import com.uet.hocvv.equiz.config.security.CustomUserDetails;
import com.uet.hocvv.equiz.domain.RestBody;
import com.uet.hocvv.equiz.domain.request.LoginRequest;
import com.uet.hocvv.equiz.domain.request.SignUpRequest;
import com.uet.hocvv.equiz.domain.response.LoginResponse;
import com.uet.hocvv.equiz.domain.response.UserDTO;
import com.uet.hocvv.equiz.service.impl.UserServiceImpl;
import com.uet.hocvv.equiz.utils.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	final
	AuthenticationManager authenticationManager;
	private final JwtTokenProvider tokenProvider;
	
	final
	UserServiceImpl userService;
	
	public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, UserServiceImpl userService) {
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
		this.userService = userService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> authenticateUser(@RequestBody LoginRequest loginRequest) throws Exception {
		System.out.println("Request body:" + loginRequest);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword()
				)
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
		
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setAccessToken(jwt);
		
		UserDTO userDTO = userService.populateUserInfo(loginRequest.getUsername());
		loginResponse.setUserDTO(userDTO);
		
		RestBody restBody = RestBody.success(loginResponse);
		return ResponseEntity.ok(restBody);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<Object> signUp(@RequestBody SignUpRequest signUpRequest) throws Exception {
		
		RestBody restBody = RestBody.success(userService.signUp(signUpRequest));
		return ResponseEntity.ok(restBody);
	}
}
