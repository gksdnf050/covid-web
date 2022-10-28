package com.covid.web.controller.api.infectionInfo;

import com.covid.web.model.entity.GenAndAgeInfo;
import com.covid.web.mapper.infectionInfo.GenAndAgeInfoMapper;
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
public class GenAndAgeInfoController {
    @Autowired
    GenAndAgeInfoMapper genAndAgeInfoMapper;

    @GetMapping("/genAndAge")
    public Map<String, GenAndAgeInfo> getCovidInfoByGenOrAge(@RequestParam (name = "genOrAge") String genOrAge){
        Map<String, GenAndAgeInfo> resultMap = new HashMap<>(); // 요청 결과

        Date today = new Date();	// 오늘 날짜
        Date yesterday = plusDate(today, -1);	// 어제 날짜

        GenAndAgeInfo todayInfo = genAndAgeInfoMapper.selectMostRecentInfoByDay(today, genOrAge);   // genOrAge에 해당하는 항목의 오늘의 가장 최근 감염현황 정보를 조회
        GenAndAgeInfo yesterdayInfo = genAndAgeInfoMapper.selectMostRecentInfoByDay(yesterday, genOrAge);   // genOrAge에 해당하는 항목의 어제의 가장 최근 감염현황 정보를 조회

        if(todayInfo == null){
            todayInfo = yesterdayInfo;
        }

        resultMap.put("today", todayInfo);
        resultMap.put("yesterday", yesterdayInfo);

        return resultMap;
    }

    @GetMapping("/genAndAge/categories")
    public Map<String, Object> getAllGenAndAge(@RequestParam(name = "pageNo") int pageNo,
                                               @RequestParam(name = "numOfRows") int numOfRows){
        Map<String, Object> resultMap = new HashMap<>();    // 요청 결과

        int start = (pageNo - 1) * numOfRows;   // 조회 시작 지점
        int totalCount = genAndAgeInfoMapper.selectDistinctCategoryCount();    // 중복되지 않은 항목(성별 or 연령)의 전체 개수
        List<String> items = genAndAgeInfoMapper.selectDistinctCategories(start, numOfRows); // 중복되지 않은 항목(성별 or 연령) 조회

        resultMap.put("totalCount", totalCount);
        resultMap.put("items", items);

        return resultMap;
    }
}
