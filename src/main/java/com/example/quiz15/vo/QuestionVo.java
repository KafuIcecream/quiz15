package com.example.quiz15.vo;

import java.util.List;

import com.example.quiz15.constants.ConstantsMessage;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class QuestionVo {
	
	private int quizId;
	
	@Min(value=1,message=ConstantsMessage.QUESTION_ID_ERROR)
	private int questionId;
	
	@NotBlank(message=ConstantsMessage.QUESTION_ERROR)
	private String question;
	
	@NotBlank(message=ConstantsMessage.QUESTION_TYPE_ERROR)
	private String type;
	
	private boolean required;
	
	private List<String> options;
	
	
	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> option) {
		this.options = option;
	}

	

	public QuestionVo(int quizId, int questionId,String question, String type, boolean required, List<String> options) {
		super();
		this.quizId = quizId;
		this.questionId = questionId;
		this.question = question;
		this.type = type;
		this.required = required;
		this.options = options;
	}

	public QuestionVo() {
		super();
	}
}
