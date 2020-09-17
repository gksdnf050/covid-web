package com.covid.web.mapper;

import com.covid.web.dto.AgeCovidInfo;
import com.covid.web.dto.CityCovidInfo;
import com.covid.web.dto.CountryCovidInfo;
import com.covid.web.dto.DomesticCovidInfo;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CovidInfoMapper {
    int insertDomesticCovidInfo(DomesticCovidInfo domesticDto);
    DomesticCovidInfo selectMostRecentDomesticCovidInfoByDay(Date stateDate);    // 기준일이 stateDate인 row중 기준 시간이 최근인 row를 조회
    int deleteAllDomesticCovidInfoByDay(Date stateDate);

    int selectDistinctCityCount();
    List<String> selectDistinctCity(int start, int limit);
    int insertCityCovidInfo(CityCovidInfo cityCovidInfo);
    CityCovidInfo selectMostRecentCityCovidInfoByDay(Date stdDay, String cityName);
    int deleteAllCityCovidInfoByDay(Date stdDay);

    int selectDistinctGenAndAgeCount();
    List<String> selectDistinctGenAndAge(int start, int limit);
    int insertGenAndAgeCovidInfo(AgeCovidInfo ageCovidInfo);
    AgeCovidInfo selectMostRecentGenAndAgeCovidInfoByDay(Date createDt, String genOrAge);
    int deleteAllGenAndAgeCovidInfoByDay(Date createDt);

    int selectDistinctCountryCount();
    List<String> selectDistinctCountry(int start, int limit);
    int insertCountryCovidInfo(CountryCovidInfo countryCovidInfo);
    CountryCovidInfo selectMostRecentCountryCovidInfoByDay(Date stdDay, String countryName);
    int deleteAllCountryCovidInfoByDay(Date stdDay);
}
