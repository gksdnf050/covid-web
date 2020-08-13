package com.covid.web.controller;

import com.covid.web.dto.ApiResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.covid.web.util.ApiUtil.getResultByResponse;
import static org.yaml.snakeyaml.util.UriEncoder.encode;

@RestController
public class SearchController {
    @GetMapping("/search")
    public String localSearch(@RequestParam(name = "query", required = true) String query) throws IOException {
        System.out.println("query" + query);
        final String DISPLAY = "5"; // 검색 결과 출력 건수 지정
        String CLIENT_ID = "ZtE5ipgXNtqEqTsmTiqO";//애플리케이션 클라이언트 아이디값";
        String CLIENT_SECRET= "3qAG0NeIGF";//애플리케이션 클라이언트 시크릿값";

        String apiURL = "https://openapi.naver.com/v1/search/local.json" + "?display=" + DISPLAY + "&query=" + encode(query);
        URL url = new URL(apiURL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
        conn.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);

        ApiResponseDto apiResponseDto = getResultByResponse(conn);
        System.out.println("ResponseCode" + apiResponseDto.getCode());
        System.out.println("ResponseResult" + apiResponseDto.getResult());

        return apiResponseDto.getResult();
    }
}
