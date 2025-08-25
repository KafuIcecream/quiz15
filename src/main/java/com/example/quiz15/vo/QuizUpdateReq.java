package com.example.quiz15.vo;

import com.example.quiz15.constants.ConstantsMessage;

import jakarta.validation.constraints.Min;

public class QuizUpdateReq extends QuizCreateReq{
	private int quizId;
	
	@Min(value=1,message=ConstantsMessage.QUIZ_ID_ERROR)
	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
}
