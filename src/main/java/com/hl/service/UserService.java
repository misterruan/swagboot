package com.hl.service;

import java.util.List;

import com.hl.model.MUser;

public interface UserService {

	//根据用户名查询
	MUser findByName(String name);


	//创建用户
	MUser createUser(MUser mUser);

}
