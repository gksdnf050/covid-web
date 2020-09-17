package com.covid.web.service;

import com.covid.web.dto.AgeCovidInfo;
import com.covid.web.dto.CityCovidInfo;
import com.covid.web.dto.CountryCovidInfo;
import com.covid.web.dto.DomesticCovidInfo;
import com.covid.web.mapper.CovidInfoMapper;
import com.covid.web.util.CovidInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.covid.web.util.ApiUtil.*;


@Service
@Transactional(readOnly = false)
public class CovidInfoService {
    @Autowired
    CovidInfoMapper covidInfoMapper;

    @Autowired
    CovidInfoUtil covidInfoUtil;

    @Scheduled(cron = "0 0/30 * * * *") // 30분에 한번씩 동작
    public int insertCurrentDomesticCovidInfo() throws IOException{
        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson";
        Date today =  new Date();
        String strToday = dateToString(today, "yyyyMMdd");
        int insertCount = 0;

        LinkedHashMap<String, Object> item = (LinkedHashMap<String, Object>) covidInfoUtil.getCovidInfoItemByUrl(url, "1", "10", strToday, strToday);

        if(item != null){
            covidInfoMapper.deleteAllDomesticCovidInfoByDay(today);

            DomesticCovidInfo domesticDto = (DomesticCovidInfo) mapToDto(item, DomesticCovidInfo.class);
            insertCount += covidInfoMapper.insertDomesticCovidInfo(domesticDto);
        }

        return insertCount;
    }

    @Scheduled(cron = "0 0/30 * * * *") // 30분에 한번씩 동작
    public int insertCurrentCityCovidInfo() throws IOException{
        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson";
        Date today =  new Date();
        String strToday = dateToString(today, "yyyyMMdd");
        int insertCount = 0;

        ArrayList items = (ArrayList) covidInfoUtil.getCovidInfoItemByUrl(url, "1", "100", strToday, strToday);

        if(items != null){
            covidInfoMapper.deleteAllCityCovidInfoByDay(today);

            for(Object item : items){
                CityCovidInfo cityCovidInfo = (CityCovidInfo) mapToDto((Map<String, Object>)item, CityCovidInfo.class);
                insertCount += covidInfoMapper.insertCityCovidInfo(cityCovidInfo);
            }
        }

        return insertCount;
    }

    @Scheduled(cron = "0 0/30 * * * *") // 30분에 한번씩 동작
    public int insertCurrentAgeCovidInfo() throws IOException{
        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19GenAgeCaseInfJson";
        Date today =  new Date();
        String strToday = dateToString(today, "yyyyMMdd");
        int insertCount = 0;

        ArrayList items = (ArrayList) covidInfoUtil.getCovidInfoItemByUrl(url, "1", "1000", strToday, strToday);

        if(items != null){
            covidInfoMapper.deleteAllGenAndAgeCovidInfoByDay(today);

            for(Object item : items){
                AgeCovidInfo ageCovidInfo = (AgeCovidInfo) mapToDto((Map<String, Object>)item, AgeCovidInfo.class);
                insertCount += covidInfoMapper.insertGenAndAgeCovidInfo(ageCovidInfo);
            }
        }

        return insertCount;
    }

    @Scheduled(cron = "0 0/30 * * * *") // 30분에 한번씩 동작
    public int insertCurrentCountryCovidInfo() throws IOException{
        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19NatInfStateJson";
        Date today =  new Date();
        String strToday = dateToString(today, "yyyyMMdd");
        int insertCount = 0;

        ArrayList items = (ArrayList) covidInfoUtil.getCovidInfoItemByUrl(url, "1", "1000", strToday, strToday);

        if(items != null){
            covidInfoMapper.deleteAllCountryCovidInfoByDay(today);

            for(Object item : items){
                CountryCovidInfo countryCovidInfo = (CountryCovidInfo) mapToDto((Map<String, Object>)item, CountryCovidInfo.class);
                insertCount += covidInfoMapper.insertCountryCovidInfo(countryCovidInfo);
            }
        }

        return insertCount;
    }
}
