package com.example.quiz15.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz15.dao.UserDao;
import com.example.quiz15.service.ifs.UserService;
import com.example.quiz15.vo.AddInfoReq;
import com.example.quiz15.vo.BasicRes;
import com.example.quiz15.vo.LoginReq;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@PostMapping(value ="user/addInfo")
	public BasicRes addInfo(@Valid @RequestBody AddInfoReq req) {
		return userService.addInfo(req.getName(),req.getPhone(), req.getEmail(),req.getAge(),req.getPassword());
	}
	
	@PostMapping(value ="user/login")
	public BasicRes login(@Valid @RequestBody LoginReq req) {
		return userService.login(req);
	}
}
