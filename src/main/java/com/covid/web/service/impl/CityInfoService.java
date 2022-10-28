package com.covid.web.service.impl;

import com.covid.web.model.entity.CityInfo;
import com.covid.web.mapper.infectionInfo.CityInfoMapper;
import com.covid.web.service.CovidInfoService;
import com.covid.web.util.infectionInfo.InfectionInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static com.covid.web.util.ApiUtil.*;

@Service
public class CityInfoService implements CovidInfoService {
    @Autowired
    InfectionInfoUtil infectionInfoUtil;

    @Autowired
    CityInfoMapper cityInfoMapper;

    @Transactional(readOnly = false)
    @Scheduled(cron = "0 0/30 * * * *") // 30분에 한번씩 동작
    public int insertTodayInfo() throws IOException {
        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson";
        Date today =  new Date();   // 데이터의 생성일, 종료일 모두 오늘 날짜로 설정하여 오늘 정보만 검색
        int insertCount = 0;    // 삽입 개수

        URL uri = infectionInfoUtil.makeInfectionInfoApiUrl(url, "1", "100", today, today); // url에 query parameter 값을 이어붙인 API 최종 요청 url
        ArrayList<Object> items = (ArrayList<Object>) getItemFromXmlResponse(uri);

        if(items != null){  // 시도별 감염현황 API로부터 데이터를 받아온 경우
            cityInfoMapper.deleteAllInfoByDay(today);   // 기준일이 오늘인 정보 모두 삭제

            for(Object item : items){   // 각각의 정보를 순회
                CityInfo cityInfo = (CityInfo) mapToDto((Map<String, Object>)item, CityInfo.class); // map을 dto로 변환
                insertCount += cityInfoMapper.insertInfo(cityInfo); // 해당 dto를 city 테이블에 삽입 (삽입한 개수를 insertCount에 더함)
            }
        }

        return insertCount;
    }
}
