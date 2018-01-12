package com.rock.service;

import com.rock.model.MUser;

public interface UserService {

	//根据用户名查询
	MUser findByName(String name);


	//创建用户
	MUser createUser(MUser mUser);

}
