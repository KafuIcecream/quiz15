package com.example.quiz15.constants;

public enum ResCodeMessage {
	SUCCESS(200, "Success!!"), //
	PASSWORD_ERROR(400, "Password Error!!"),//
	NOT_FOUND(404, "Not Found!!"),//
	PASSWORD_MISMATCH(400, "Password Mismatch!!"),//
	EMAIL_EXISTS(400,"Email Exists1!!"),
	QUIZ_CREATE_ERROR(400,"Quiz Create Error!!"),
	ADD_INFO_ERROR(400,"Add Info Error!!"),
	DATE_FORMAT_ERROR(400,"Date Format Error!!"),
	QUESTION_TYPE_ERROR(400,"Question Type Error!!"),
	OPTIONS_INSUFFICIENT(400,"Option Instfficinet!!"),
	TEXT_HAS_OPTIONS_ERROR(400,"Text Has Options Error!!"),
	QUIZ_UPDATE_FAILED(400,"Quiz Update Failed!!"),
	OPTIONS_TRANSFER_ERROR(400,"Options Transfer Errpr!!"),
	QUIZ_ERROR(400,"Quiz_Error!!");
	
	
	
	private int code;
	private String message;
	
	private ResCodeMessage(int code,String message) {
		this.code=code;
		this.message=message;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
}
