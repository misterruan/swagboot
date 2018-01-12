package com.rock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rock.model.MUser;
import com.rock.repository.UserRepository;
import com.rock.service.UserService;
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
