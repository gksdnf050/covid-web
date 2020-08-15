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
        final int SIZE = 10; // 검색 결과 출력 건수 지정
        String kakaoRestApiKey = ""; // 애플리케이션 클라이언트 아이디값";

        String apiURL = "https://dapi.kakao.com/v2/local/search/keyword.json" + "?size=" + SIZE + "&query=" + encode(query);
        URL url = new URL(apiURL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Authorization", kakaoRestApiKey);

        ApiResponseDto apiResponseDto = getResultByResponse(conn);
        System.out.println("ResponseCode" + apiResponseDto.getCode());
        System.out.println("ResponseResult" + apiResponseDto.getResult());

        return apiResponseDto.getResult();
    }
}
