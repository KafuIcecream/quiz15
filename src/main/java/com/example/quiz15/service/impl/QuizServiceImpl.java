package com.example.quiz15.service.impl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quiz15.constants.QuestionType;
import com.example.quiz15.constants.ResCodeMessage;
import com.example.quiz15.dao.QuestionDao;
import com.example.quiz15.dao.QuizDao;
import com.example.quiz15.entity.Question;
import com.example.quiz15.entity.Quiz;
import com.example.quiz15.service.ifs.QuizService;
import com.example.quiz15.vo.BasicRes;
import com.example.quiz15.vo.QuestionRes;
import com.example.quiz15.vo.QuestionVo;
import com.example.quiz15.vo.QuizCreateReq;
import com.example.quiz15.vo.QuizUpdateReq;
import com.example.quiz15.vo.SearchRes;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;




@Service
public class QuizServiceImpl implements QuizService{
	
	private ObjectMapper mapper=new ObjectMapper();
	
	@Autowired
	private QuizDao quizDao;
	@Autowired
	private QuestionDao questionDao;
	
	@Transactional(rollbackOn = Exception.class)
	@Override
	public BasicRes create(QuizCreateReq req) throws Exception{
		if(req.getStartDate().isAfter(req.getEndDate()) || req.getStartDate().isBefore(LocalDate.now())) {
			return new BasicRes(ResCodeMessage.DATE_FORMAT_ERROR.getCode(),ResCodeMessage.DATE_FORMAT_ERROR.getMessage());
		}
		try {
			BasicRes checkRes=checkDate(req.getStartDate(),req.getEndDate());
			if(checkRes.getCode()!=200) {
				return checkRes;
			}
		
			quizDao.insert(req.getName(), req.getDescription(), req.getStartDate(),req.getEndDate(),req.isPublished());
			List<QuestionVo>questionVoList=req.getQuestionList();
			for(QuestionVo vo:questionVoList) {
				checkRes=checkQuestionType(vo);
				if(checkRes.getCode()!=200) {
					throw new Exception(checkRes.getMessage());
				}
				List<String> optionsList=vo.getOptions();
				String str=mapper.writeValueAsString(optionsList);
				int quizId=quizDao.getMaxQuizId();
				questionDao.insert(quizId, vo.getQuestionId(), vo.getQuestion(), vo.getType(),vo.isRequired(),str);
			}
			return new BasicRes(ResCodeMessage.SUCCESS.getCode(),ResCodeMessage.SUCCESS.getMessage());
		}
		catch(Exception E) {
			System.out.println("Exception錯誤"+E);
			throw E;
		}
	}
	
	
	
	
	@Transactional(rollbackOn = Exception.class)
	@Override
	public BasicRes update(QuizUpdateReq req) throws Exception {
		
		try {
			int count=quizDao.getCountByQuizId(req.getQuizId());
			if(count!=1) {
				return new BasicRes(ResCodeMessage.NOT_FOUND.getCode(),ResCodeMessage.NOT_FOUND.getMessage());
			}
			BasicRes checkRes=checkDate(req.getStartDate(),req.getEndDate());
			if(checkRes.getCode()!=200) {
				return checkRes;
			}
			int quizId=req.getQuizId();
			questionDao.deleteByQuizId(quizId);
			List<QuestionVo>questionVoList=req.getQuestionList();
			for(QuestionVo vo:questionVoList) {
				checkRes=checkQuestionType(vo);
				if(checkRes.getCode()!=200) {
					throw new Exception(checkRes.getMessage());
				}
				int updateRes=quizDao.update(quizId, req.getName(), req.getDescription(), req.getStartDate(),req.getEndDate(), req.isPublished());
				if(updateRes!=1) {
					return new BasicRes(ResCodeMessage.QUIZ_UPDATE_FAILED.getCode(),ResCodeMessage.QUIZ_UPDATE_FAILED.getMessage());
				}
				List<String> optionsList=vo.getOptions();
				String str=mapper.writeValueAsString(optionsList);
				questionDao.insert(req.getQuizId(), vo.getQuestionId(), vo.getQuestion(), vo.getType(),vo.isRequired(),str);
			}
		}
		catch(Exception E){
			throw E;
		}
		return new BasicRes(ResCodeMessage.SUCCESS.getCode(),ResCodeMessage.SUCCESS.getMessage());
		
	}
	
	
	
	
	private BasicRes checkQuestionType(QuestionVo vo) {
		String type=vo.getType();
		if(!type.equalsIgnoreCase(QuestionType.SINGLE.getType()) && !type.equalsIgnoreCase(QuestionType.MULTI.getType()) && !type.equalsIgnoreCase(QuestionType.TEXT.getType())){
			return new BasicRes(ResCodeMessage.QUESTION_TYPE_ERROR.getCode(),ResCodeMessage.QUESTION_TYPE_ERROR.getMessage());
		}
		
		if(!type.equalsIgnoreCase(QuestionType.TEXT.getType())){
			if(vo.getOptions().size()<2) {
				return new BasicRes(ResCodeMessage.OPTIONS_INSUFFICIENT.getCode(),ResCodeMessage.OPTIONS_INSUFFICIENT.getMessage());
			}
		}
		else {
			if(vo.getOptions()!=null&&vo.getOptions().size()>0) {
				return new BasicRes(ResCodeMessage.TEXT_HAS_OPTIONS_ERROR.getCode(),ResCodeMessage.TEXT_HAS_OPTIONS_ERROR.getMessage());
			}
		}
		return new BasicRes(ResCodeMessage.SUCCESS.getCode(),ResCodeMessage.SUCCESS.getMessage());
	}

	
	
	
	private BasicRes checkDate(LocalDate startDate,LocalDate endDate) {
		if(startDate.isAfter(endDate) || startDate.isBefore(LocalDate.now())) {
			return new BasicRes(ResCodeMessage.DATE_FORMAT_ERROR.getCode(),ResCodeMessage.DATE_FORMAT_ERROR.getMessage());
		}
		return new BasicRes(ResCodeMessage.SUCCESS.getCode(),ResCodeMessage.SUCCESS.getMessage());
	}





	@Override
	public SearchRes getAllQuizs() {
		List<Quiz>list=quizDao.getAll();
		return new SearchRes(ResCodeMessage.SUCCESS.getCode(),ResCodeMessage.SUCCESS.getMessage(),list);
		
	}





	@Override
	public QuestionRes getQuizByQuizId(int quizId) {
		List<QuestionVo> questionVoList=new ArrayList<>();
		List<Question> list=questionDao.getQuestionsByQuizId(quizId);
		for(Question item:list) {
			String str=item.getOptions();
			try {
				List<String>optionList=mapper.readValue(str, new TypeReference<>() {});
				QuestionVo vo=new QuestionVo(item.getQuizId(),item.getQuestionId(),item.getQuestion(),item.getType(),item.isRequired(),optionList);
				questionVoList.add(vo);
			}
			catch(Exception E) {
				return new QuestionRes(ResCodeMessage.OPTIONS_TRANSFER_ERROR.getCode(),ResCodeMessage.OPTIONS_TRANSFER_ERROR.getMessage());
			}
		}
		return new QuestionRes(ResCodeMessage.SUCCESS.getCode(),ResCodeMessage.SUCCESS.getMessage(),questionVoList);
	}
}
