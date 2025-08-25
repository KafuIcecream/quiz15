package com.example.quiz15.service.ifs;

import com.example.quiz15.vo.AddInfoReq;
import com.example.quiz15.vo.BasicRes;
import com.example.quiz15.vo.LoginReq;

public interface UserService {
	public BasicRes addInfo(String name,String phone,String email,int age,String password);
	public BasicRes login(LoginReq req);
}
