package com.covid.web.repository;

import com.covid.web.model.entity.GenAndAgeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GenAndAgeInfoRepository extends JpaRepository<GenAndAgeInfo, Long> {
    @Query(value = "select * from restaurant", nativeQuery = true)
    int selectDistinctCategoryCount();  // 중복되지 않은 항목의 전체 개수
    @Query(value = "select * from restaurant", nativeQuery = true)
    List<String> selectDistinctCategories(int start, int limit);    // 중복되지 않은 항목 조회 (start부터 numOfRows만큼 조회)
    @Query(value = "select * from restaurant", nativeQuery = true)
    GenAndAgeInfo selectMostRecentInfoByDay(Date createDt, String genOrAge);    // genOrAge에 해당하는 항목의 기준일이 stdDay인 정보 중 가장 최근의 정보를 조회
    @Query(value = "select * from restaurant", nativeQuery = true)
    int insertInfo(GenAndAgeInfo genAndAgeInfo);
    @Query(value = "select * from restaurant", nativeQuery = true)
    int deleteAllInfoByDay(Date createDt);  // 등록일시분초가 createDt인 정보 삭제
}
