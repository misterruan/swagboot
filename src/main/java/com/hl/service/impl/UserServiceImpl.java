package com.hl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.hl.model.MUser;
import com.hl.repository.UserRepository;
import com.hl.service.UserService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	
	//使用JPA
	public MUser findByName(String name){
		return userRepository.findByName(name);
	}

	public MUser createUser(MUser mUser) {
        MUser user = userRepository.save(mUser);
        //test transation
        //int i =10/0;
        return user;
	}


}
