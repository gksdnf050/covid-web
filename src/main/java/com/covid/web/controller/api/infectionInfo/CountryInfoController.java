package com.covid.web.controller.api.infectionInfo;

import com.covid.web.dto.infectionInfo.CountryInfo;
import com.covid.web.mapper.infectionInfo.CountryInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.covid.web.util.ApiUtil.plusDate;

@RestController
@RequestMapping("/api/infection-info")
public class CountryInfoController {
    @Autowired
    CountryInfoMapper countryInfoMapper;

    @GetMapping("/country")
    public Map<String, CountryInfo> getCovidInfoByCountry(@RequestParam("name") String countryName){
        Map<String, CountryInfo> resultMap = new HashMap<>();   // 요청 결과

        Date today = new Date();	// 오늘 날짜
        Date yesterday = plusDate(today, -1);	// 어제 날짜

        CountryInfo todayInfo = countryInfoMapper.selectMostRecentInfoByDay(today, countryName);    // countryName에 해당하는 국가의 오늘의 가장 최근 감염현황 정보를 조회
        CountryInfo yesterdayInfo = countryInfoMapper.selectMostRecentInfoByDay(yesterday, countryName);     // countryName에 해당하는 국가의 어제의 가장 최근 감염현황 정보를 조회

        if(todayInfo == null){  // 오늘의 시도별 감연현황이 없다면
            todayInfo = yesterdayInfo;   // 오늘 감염현황 = 어제 감염현황
        }

        resultMap.put("today", todayInfo);
        resultMap.put("yesterday", yesterdayInfo);

        return resultMap;
    }

    @GetMapping("/country/categories")
    public Map<String, Object> getAllCountry(@RequestParam("pageNo") int pageNo,
                                             @RequestParam("numOfRows") int numOfRows){
        Map<String, Object> resultMap = new HashMap<>();     // 요청 결과

        int start = (pageNo - 1) * numOfRows; // 조회 시작 지점
        int totalCount = countryInfoMapper.selectDistinctCategoryCount();    // 중복되지 않은 국가명의 전체 개수
        List<String> items = countryInfoMapper.selectDistinctCategories(start, numOfRows); // 중복되지 않은 국가명 조회 (start부터 numOfRows만큼 조회)

        resultMap.put("totalCount", totalCount);
        resultMap.put("items", items);

        return resultMap;
    }
}
