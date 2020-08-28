package com.covid.web.controller.api;

import com.covid.web.dto.ApiResponseDto;
import com.covid.web.util.ApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CovidController {
    @Autowired
    private ApiUtil apiUtil;

    @GetMapping("/hospital")
    public String getAllCovidHospital() throws IOException {
        String pageNo = "1";
        String numOfRows = "1000000";
        String spclAdmTyCd = null;

        ApiResponseDto apiResponseDto = apiUtil.covidHospital(pageNo, numOfRows, spclAdmTyCd);

        int responseCode = apiResponseDto.getCode();
        String responseResult = apiResponseDto.getResult();

        return responseResult;
    }


    @GetMapping("/restaurant")
    public ArrayList getCovidRestaurant(@RequestParam("start") String startIndex, @RequestParam("end") String endIndex) throws IOException {
        ApiResponseDto apiResponseDto = apiUtil.covidRestaurant(startIndex, endIndex);
        int responseCode = apiResponseDto.getCode();
        String responseResult = apiResponseDto.getResult();

        Map temp = (Map) apiUtil.parseStringToMap(responseResult).get("Grid_20200713000000000605_1");
       
        return (ArrayList) temp.get("row");
    }

}
