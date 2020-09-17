package com.covid.web.controller.api;

import com.covid.web.dto.*;
import com.covid.web.mapper.CovidHospitalMappper;
import com.covid.web.mapper.CovidInfoMapper;
import com.covid.web.mapper.CovidRestaurantMapper;
import com.covid.web.util.CovidInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.covid.web.util.ApiUtil.plusDate;

@RestController
@RequestMapping("/api")
public class CovidInfoController {
	@Autowired
	CovidRestaurantMapper restaurantMapper;

	@Autowired
	CovidHospitalMappper hospitalMappper;

	@Autowired
	CovidInfoMapper covidInfoMapper;

	@Autowired
	CovidInfoUtil covidInfoUtil;

	@GetMapping("/hospital")
	public List<CovidHospital> getAllCovidHospital() {
		return hospitalMappper.getHospital();
	}


	@GetMapping("/restaurant")
	public List<CovidRestaurant> getCovidRestaurant() {
		return restaurantMapper.getRestaurant();
	}

	@GetMapping("/domesticInfo")
	public Map<String, DomesticCovidInfo> getDomesticCovidInfo() {
		Map<String, DomesticCovidInfo> resultMap = new HashMap<>();

		Date today = plusDate(new Date(), -1);	// 오늘 날짜	todo :
		Date yesterday = plusDate(today, -2);	// 어제 날짜	todo :

		DomesticCovidInfo todayInfo = covidInfoMapper.selectMostRecentDomesticCovidInfoByDay(today);	// 오늘 국내 정보 조회 (api를 데이터베이스에 저장할 때, 해당 날짜의 모든 정보를 지우고 insert 하기 때문에 정보가 최신 데이터를 조회할 필요 없음.)
		DomesticCovidInfo yesterdayInfo = covidInfoMapper.selectMostRecentDomesticCovidInfoByDay(yesterday);	// 어제 국내 정보 조회
	
		if(todayInfo == null){	// 아직 오늘 정보가 없다면 어제 정보를 오늘 정보로 하여 응답
			todayInfo = yesterdayInfo;
		}

		resultMap.put("today", todayInfo);
		resultMap.put("yesterday", yesterdayInfo);

		return resultMap;
	}

	@GetMapping("/city")
	public Map<String, Object> getAllCity(@RequestParam(name = "pageNo") int pageNo,
								   @RequestParam(name = "numOfRows") int numOfRows){
		Map<String, Object> resultMap = new HashMap<>();

		int start = (pageNo-1) * numOfRows;
		int totalCount = covidInfoMapper.selectDistinctCityCount();
		List<String> items = covidInfoMapper.selectDistinctCity(start, numOfRows);

		resultMap.put("totalCount", totalCount);
		resultMap.put("items", items);

		return resultMap;
	}

	@GetMapping("/cityInfo")
	public Map<String, CityCovidInfo> getCovidInfoByCity(@RequestParam(name = "city", required = true) String cityName) throws IOException{
		Map<String, CityCovidInfo> resultMap = new HashMap<>();

		Date today = plusDate(new Date(), -1);	// 오늘 날짜	todo :
		Date yesterday = plusDate(today, -2);	// 어제 날짜	todo :

		CityCovidInfo todayInfo = covidInfoMapper.selectMostRecentCityCovidInfoByDay(today, cityName);
		CityCovidInfo yesterdayInfo = covidInfoMapper.selectMostRecentCityCovidInfoByDay(yesterday, cityName);

		if(todayInfo == null){
			todayInfo = yesterdayInfo;
		}

		resultMap.put("today", todayInfo);
		resultMap.put("yesterday", yesterdayInfo);

		return resultMap;
	}

	@GetMapping("/genAndAge")
	public Map<String, Object> getAllGenAndAge(@RequestParam(name = "pageNo") int pageNo,
										@RequestParam(name = "numOfRows") int numOfRows){
		Map<String, Object> resultMap = new HashMap<>();

		int start = (pageNo-1) * numOfRows;
		int totalCount = covidInfoMapper.selectDistinctGenAndAgeCount();
		List<String> items = covidInfoMapper.selectDistinctGenAndAge(start, numOfRows);

		resultMap.put("totalCount", totalCount);
		resultMap.put("items", items);

		return resultMap;
	}

	@GetMapping("/genAndAgeInfo")
	public Map<String, AgeCovidInfo> getCovidInfoByAge(@RequestParam (name = "genAndAge") String genAndAge) throws IOException{
		Map<String, AgeCovidInfo> resultMap = new HashMap<>();

		Date today = plusDate(new Date(), -1);	// 오늘 날짜	todo :
		Date yesterday = plusDate(today, -2);	// 어제 날짜	todo :

		AgeCovidInfo todayInfo = covidInfoMapper.selectMostRecentGenAndAgeCovidInfoByDay(today, genAndAge);
		AgeCovidInfo yesterdayInfo = covidInfoMapper.selectMostRecentGenAndAgeCovidInfoByDay(yesterday, genAndAge);

		if(todayInfo == null){
			todayInfo = yesterdayInfo;
		}

		resultMap.put("today", todayInfo);
		resultMap.put("yesterday", yesterdayInfo);

		return resultMap;
	}

	@GetMapping("/country")
	public Map<String, Object> getAllCountry(@RequestParam(name = "pageNo") int pageNo,
									  @RequestParam(name = "numOfRows") int numOfRows){
		Map<String, Object> resultMap = new HashMap<>();

		int start = (pageNo-1) * numOfRows;
		int totalCount = covidInfoMapper.selectDistinctCountryCount();
		List<String> items = covidInfoMapper.selectDistinctCountry(start, numOfRows);

		resultMap.put("totalCount", totalCount);
		resultMap.put("items", items);

		return resultMap;
	}

	@GetMapping("/countryInfo")
	public Map<String, CountryCovidInfo> getCovidInfoByCountry(@RequestParam(name = "country") String countryName){

		Map<String, CountryCovidInfo> resultMap = new HashMap<>();
		// page, numOfRows 4개씩 했을 때 2페이지

		Date today = plusDate(new Date(), -1);	// 오늘 날짜	todo :
		Date yesterday = plusDate(today, -2);	// 어제 날짜	todo :

		CountryCovidInfo todayInfo = covidInfoMapper.selectMostRecentCountryCovidInfoByDay(today, countryName);
		CountryCovidInfo yesterdayInfo = covidInfoMapper.selectMostRecentCountryCovidInfoByDay(yesterday, countryName);

		if(todayInfo == null){
			todayInfo = yesterdayInfo;
		}

		resultMap.put("today", todayInfo);
		resultMap.put("yesterday", yesterdayInfo);

		return resultMap;
	}
}
