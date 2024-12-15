package com.moriya.project_moriya_java.service;

import com.moriya.project_moriya_java.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends  JpaRepository<Users,Long>{
    Users findByName(String name);


}
