package com.covid.web.controller.api;

import com.covid.web.dto.ApiResponseDto;
import com.covid.web.util.ApiUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AddressSearchController {
/*    @Autowired
    private ApiUtil apiUtil;

    @GetMapping("/instantSearch")
    public String localSearch(@RequestParam(name = "query", required = true) String query) throws IOException {
        String size = "10"; // 검색 결과 출력 건수 지정

        ApiResponseDto apiResponseDto = apiUtil.KakaolocalSearchApi(query, size);

        int responseCode = apiResponseDto.getCode();
        String responseResult = apiResponseDto.getResult();

        return responseResult;
    }

    @GetMapping("/addressToPoint")
    public String addressToPoint(@RequestParam(name = "query", required = true) String query) throws IOException{
        String size = "1"; // 검색 결과 출력 건수 지정

        ApiResponseDto apiResponseDto = apiUtil.KakaolocalSearchApi(query, size);

        int responseCode = apiResponseDto.getCode();
        String responseResult = apiResponseDto.getResult();

        return responseResult;
    } */
}
