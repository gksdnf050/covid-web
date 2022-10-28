package com.covid.web.controller.api.infectionInfo;

import com.covid.web.model.entity.DomesticInfo;
import com.covid.web.repository.DomesticInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.covid.web.util.ApiUtil.plusDate;

@RestController
@RequestMapping("/api/infection-info")
public class DomesticInfoController {
    @Autowired
    DomesticInfoRepository domesticInfoRepository;

    @GetMapping("/domestics")
    public Map<String, DomesticInfo> getDomesticCovidInfo() {
        Map<String, DomesticInfo> resultMap = new HashMap<>();

        Date today = new Date();	// 오늘 날짜
        Date yesterday = plusDate(today, -1);	// 어제 날짜

        DomesticInfo todayInfo = domesticInfoRepository.selectMostRecentInfoByDay(today);	// 오늘 국내 정보 조회 (api를 주기적으로 요청하여 데이터베이스에 저장할 때, 해당 날짜의 모든 정보를 지우고 insert 하기 때문에  최신 데이터를 조회할 필요 없음.)
        DomesticInfo yesterdayInfo = domesticInfoRepository.selectMostRecentInfoByDay(yesterday);	// 어제 국내 정보 조회

        if(todayInfo == null){	// 아직 오늘 정보가 없다면 어제 정보를 오늘 정보로 하여 응답
            todayInfo = yesterdayInfo;  // 오늘 감염현황 = 어제 감염현황
        }

        resultMap.put("today", todayInfo);
        resultMap.put("yesterday", yesterdayInfo);

        return resultMap;
    }
}
