package com.covid.web.mapper.infectionInfo;

import com.covid.web.model.entity.CityInfo;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CityInfoMapper {
    int selectDistinctCategoryCount();  // 중복되지 않은 시도명의 전체 개수
    List<String> selectDistinctCategories(int start, int limit);      // 중복되지 않은 시도명 조회 (start부터 numOfRows만큼 조회)
    CityInfo selectMostRecentInfoByDay(Date stdDay, String cityName);   // 시도명이 cityName이고, 기준일이 stdDay인 정보 중 가장 최근의 정보를 조회

    int insertInfo(CityInfo cityInfo); 
    int deleteAllInfoByDay(Date stdDay);    // 기준일이 stdDay인 정보 삭제
}
