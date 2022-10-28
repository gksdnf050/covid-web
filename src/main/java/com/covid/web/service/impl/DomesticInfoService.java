package com.covid.web.service.impl;

import com.covid.web.model.entity.DomesticInfo;
import com.covid.web.mapper.infectionInfo.DomesticInfoMapper;
import com.covid.web.service.CovidInfoService;
import com.covid.web.util.infectionInfo.InfectionInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;

import static com.covid.web.util.ApiUtil.*;

@Service
public class DomesticInfoService implements CovidInfoService {
    @Autowired
    InfectionInfoUtil infectionInfoUtil;

    @Autowired
    DomesticInfoMapper domesticInfoMapper;

    @Transactional(readOnly = false)
    @Scheduled(cron = "0 0/30 * * * *") // 30분에 한번씩 동작
    public int insertTodayInfo() throws IOException {
        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson";
        Date today =  new Date();   // 데이터의 생성일, 종료일 모두 오늘 날짜로 설정하여 오늘 정보만 검색
        int insertCount = 0;    // 삽입 개수

        URL uri = infectionInfoUtil.makeInfectionInfoApiUrl(url, "1", "10", today, today);  // url에 query parameter 값을 이어붙인 API 최종 요청 url
        LinkedHashMap<String, Object> item = (LinkedHashMap<String, Object>) getItemFromXmlResponse(uri);

        if(item != null){    // 국내 감염현황 API로부터 데이터를 받아온 경우
            domesticInfoMapper.deleteAllInfoByDay(today);   // 기준일이 오늘인 정보 모두 삭제

            DomesticInfo domesticDto = (DomesticInfo) mapToDto(item, DomesticInfo.class);   // map을 dto로 변환
            insertCount += domesticInfoMapper.insertInfo(domesticDto);   // 해당 dto를 domestic 테이블에 삽입 (삽입한 개수를 insertCount에 더함)
        }

        return insertCount;
    }
}
