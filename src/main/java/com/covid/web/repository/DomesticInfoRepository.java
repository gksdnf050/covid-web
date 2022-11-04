package com.covid.web.repository;

import com.covid.web.model.entity.DomesticInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
public interface DomesticInfoRepository {
    DomesticInfo selectMostRecentInfoByDay(Date stateDate);    // 기준일이 stateDate인 row중 기준 시간이 가장 최근인 row를 조회
    int insertInfo(DomesticInfo domesticDto);
    int deleteAllInfoByDay(Date stateDate); // 기준일이 stateDate인 정보 삭제
}
