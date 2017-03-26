package com.hl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hl.model.MUser;

public interface UserRepository extends JpaRepository<MUser, Long> {

    MUser findByName(String name);

    MUser save(MUser s);
}