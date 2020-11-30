package com.uet.hocvv.equiz.domain;

import com.uet.hocvv.equiz.utils.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestBody {
	
	private int statusCode;
	private String message;
	private Object data;
	
	public static RestBody newInstance(int statusCode, String message, Object data) {
		return new RestBody(statusCode, message, data);
	}
	
	public static RestBody success(Object data) {
		return newInstance(ResponseCode.SUCCESS, "success", data);
	}
	
	public static RestBody error(String message) {
		return newInstance(ResponseCode.ERROR, message, null);
	}
	
	public static RestBody error(String message, Object data) {
		return newInstance(ResponseCode.ERROR, message, data);
	}
	
}
