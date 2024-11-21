package com.moriya.project_moriya_java.service;

import com.moriya.project_moriya_java.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends  JpaRepository<Users,Long>{
}
