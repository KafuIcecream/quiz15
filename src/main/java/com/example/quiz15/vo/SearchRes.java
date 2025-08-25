package com.example.quiz15.vo;

import java.util.List;

import com.example.quiz15.entity.Quiz;

public class SearchRes extends BasicRes{
	private List<Quiz>quizList;

	public SearchRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchRes(List<Quiz> quizList) {
		super();
		this.quizList = quizList;
	}

	public SearchRes(int code, String message,List<Quiz> quizList) {
		super(code, message);
	}

	public List<Quiz> getQuizList() {
		return quizList;
	}

	public void setQuizList(List<Quiz> quizList) {
		this.quizList = quizList;
	}
	
}
