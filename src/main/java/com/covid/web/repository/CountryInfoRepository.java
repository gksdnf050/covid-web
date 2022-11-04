package com.covid.web.repository;

import com.covid.web.model.entity.CountryInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
public interface CountryInfoRepository {
    int selectDistinctCategoryCount();   // 중복되지 않은 국가명의 전체 개수
    List<String> selectDistinctCategories(int start, int limit);   // 중복되지 않은 국가명 조회 (start부터 numOfRows만큼 조회)
    CountryInfo selectMostRecentInfoByDay(Date stdDay, String countryName);  // 국가명이 countryName이고, 기준일이 stdDay인 정보 중 가장 최근의 정보를 조회
    int insertInfo(CountryInfo countryInfo);
    int deleteAllInfoByDay(Date stdDay);    // 기준일이 stdDay인 정보 삭제
}
