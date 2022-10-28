package com.covid.web.mapper.infectionInfo;

import com.covid.web.model.entity.DomesticInfo;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DomesticInfoMapper {
    DomesticInfo selectMostRecentInfoByDay(Date stateDate);    // 기준일이 stateDate인 row중 기준 시간이 가장 최근인 row를 조회

    int insertInfo(DomesticInfo domesticDto);
    int deleteAllInfoByDay(Date stateDate); // 기준일이 stateDate인 정보 삭제
}
