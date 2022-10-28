package com.covid.web.controller.api;

import com.covid.web.dto.ApiResponse;
import com.covid.web.util.KakaoMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/map")
public class AddressController {
   @Autowired
    private KakaoMapUtil kakaoMapUtil;

    @GetMapping("/search-recommend")
    public String localSearch(@RequestParam("query") String query) throws IOException {
        String size = "10"; // 검색 결과 출력 건수 지정

        ApiResponse apiResponse = kakaoMapUtil.searchRecommend(query, size);

        return apiResponse.getResult();
    }

    @GetMapping("/address-to-point")
    public String addressToPoint(@RequestParam("query") String query) throws IOException{
        String size = "1"; // 검색 결과 출력 건수 지정

        ApiResponse apiResponse = kakaoMapUtil.searchRecommend(query, size);

        return apiResponse.getResult();
    }
}
