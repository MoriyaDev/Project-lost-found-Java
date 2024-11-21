package com.moriya.project_moriya_java.service;

import com.moriya.project_moriya_java.model.Msgs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MsgsRepository extends  JpaRepository<Msgs,Long>{
    List<Msgs> findByAdID(Long adID);



}
