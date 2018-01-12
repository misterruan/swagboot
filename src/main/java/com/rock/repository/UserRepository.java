package com.rock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rock.model.MUser;

public interface UserRepository extends JpaRepository<MUser, Long> {

    MUser findByName(String name);

    MUser save(MUser s);
}