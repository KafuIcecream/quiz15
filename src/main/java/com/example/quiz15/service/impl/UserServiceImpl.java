package com.example.quiz15.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quiz15.constants.ResCodeMessage;
import com.example.quiz15.dao.UserDao;
import com.example.quiz15.entity.User;
import com.example.quiz15.service.ifs.UserService;
import com.example.quiz15.vo.AddInfoReq;
import com.example.quiz15.vo.BasicRes;
import com.example.quiz15.vo.LoginReq;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
	
	@Override
	public BasicRes addInfo(String name,String phone,String email,int age,String password) {
		System.out.println("測試");
		int count=userDao.getCountByEmail(email);
		if(count==1) {
			return new BasicRes(ResCodeMessage.EMAIL_EXISTS.getCode(),ResCodeMessage.EMAIL_EXISTS.getMessage());
		}
		try {
			userDao.addInfo(name,phone,email,age,encoder.encode(password));
			return new BasicRes(ResCodeMessage.SUCCESS.getCode(),ResCodeMessage.SUCCESS.getMessage());
		}
		catch(Exception E){
			System.out.println("抱錯"+E);
			return new BasicRes(ResCodeMessage.ADD_INFO_ERROR.getCode(),ResCodeMessage.ADD_INFO_ERROR.getMessage());
		}
	}

	@Override
	public BasicRes login(LoginReq req) {
		System.out.println("測試");
		User user =userDao.getByEmail(req.getEmail());
		if(user==null) {
			return new BasicRes(ResCodeMessage.NOT_FOUND.getCode(),ResCodeMessage.NOT_FOUND.getMessage());
		}
		if(encoder.matches(req.getPassword(),user.getPassword())==false) {
			return new BasicRes(ResCodeMessage.PASSWORD_MISMATCH.getCode(),ResCodeMessage.PASSWORD_MISMATCH.getMessage());
		}
		
		return new BasicRes(ResCodeMessage.SUCCESS.getCode(),ResCodeMessage.SUCCESS.getMessage());
		
		
		
	}
}
