package com.moriya.project_moriya_java.service;

import com.moriya.project_moriya_java.model.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AdsRepository extends  JpaRepository<Ads, Long> {
}
