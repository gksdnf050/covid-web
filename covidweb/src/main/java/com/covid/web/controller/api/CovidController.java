package com.covid.web.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid.web.dto.ApiResponseDto;
import com.covid.web.util.ApiUtil;

@RestController
@RequestMapping("/api")
@PropertySource("classpath:application.properties")
public class CovidController {
    @Value("${open-api.key.covid-restaurant-api-key}")
    private String covidRestaurantApiKey;

    @Value("${open-api.key.covid-hospital-api-key}")
    private String covidHospitalApiKey; // 애플리케이션 클라이언트 아이디값";

    @GetMapping("/hospital")
    public String getAllCovidHospital() throws IOException {
        String pageNo = "1";
        String numOfRows = "1000000";
        String spclAdmTyCd = null;

        ApiUtil apiUtil = new ApiUtil();
        ApiResponseDto apiResponseDto = apiUtil.covidHospital(covidHospitalApiKey, pageNo, numOfRows, spclAdmTyCd);

        int responseCode = apiResponseDto.getCode();
        String responseResult = apiResponseDto.getResult();

        return responseResult;
    }


    @GetMapping("/restaurant")
    public ArrayList getCovidRestaurant(@RequestParam("start") String startIndex, @RequestParam("end") String endIndex) throws IOException {
        ApiUtil apiUtil = new ApiUtil();
        ApiResponseDto apiResponseDto = apiUtil.covidRestaurant(covidRestaurantApiKey, startIndex, endIndex);

        int responseCode = apiResponseDto.getCode();
        String responseResult = apiResponseDto.getResult();

        Map temp = (Map) apiUtil.parseStringToMap(responseResult).get("Grid_20200713000000000605_1");
       
        return (ArrayList) temp.get("row");
    }

}
