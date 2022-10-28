package com.covid.web.repository;

import com.covid.web.model.entity.DomesticInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DomesticInfoRepository extends JpaRepository<DomesticInfo, Long> {
    @Query(value = "select * from restaurant", nativeQuery = true)
    DomesticInfo selectMostRecentInfoByDay(Date stateDate);    // 기준일이 stateDate인 row중 기준 시간이 가장 최근인 row를 조회
    @Query(value = "select * from restaurant", nativeQuery = true)
    int insertInfo(DomesticInfo domesticDto);
    @Query(value = "select * from restaurant", nativeQuery = true)
    int deleteAllInfoByDay(Date stateDate); // 기준일이 stateDate인 정보 삭제
}
