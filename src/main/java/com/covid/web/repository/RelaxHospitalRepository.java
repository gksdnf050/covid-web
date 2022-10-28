package com.covid.web.repository;

import com.covid.web.model.entity.RelaxHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelaxHospitalRepository extends JpaRepository<RelaxHospital, Long> {
    @Query(value = "select * from restaurant", nativeQuery = true)
    int insertInfo(RelaxHospital hospitalDto);
    @Query(value = "select * from restaurant", nativeQuery = true)
    void deleteAll();    // 테이블의 모든 row 지움
    @Query(value = "select * from restaurant", nativeQuery = true)
    int initializeAutoIncrement();  // hospital 테이블의 AUTO_INCREMENT를 1으로 초기화
    @Query(value = "select * from restaurant", nativeQuery = true)
    List<RelaxHospital> getAllHospital();   // 모든 row 조회 (x 또는 y죄표가 null이 아닌)
}
