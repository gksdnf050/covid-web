package com.covid.web.repository;

import com.covid.web.model.entity.CityInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CityInfoRepository extends JpaRepository<CityInfo, Long> {
    @Query(value = "select * from restaurant", nativeQuery = true)
    int selectDistinctCategoryCount();  // 중복되지 않은 시도명의 전체 개수
    @Query(value = "select * from restaurant", nativeQuery = true)
    List<String> selectDistinctCategories(int start, int limit);      // 중복되지 않은 시도명 조회 (start부터 numOfRows만큼 조회)
    @Query(value = "select * from restaurant", nativeQuery = true)
    CityInfo selectMostRecentInfoByDay(Date stdDay, String cityName);   // 시도명이 cityName이고, 기준일이 stdDay인 정보 중 가장 최근의 정보를 조회
    @Query(value = "select * from restaurant", nativeQuery = true)
    int insertInfo(CityInfo cityInfo);
    @Query(value = "select * from restaurant", nativeQuery = true)
    int deleteAllInfoByDay(Date stdDay);    // 기준일이 stdDay인 정보 삭제
}
