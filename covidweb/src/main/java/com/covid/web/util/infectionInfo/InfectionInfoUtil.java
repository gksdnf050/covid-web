package com.covid.web.util.infectionInfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import static com.covid.web.util.ApiUtil.dateToString;
import static org.yaml.snakeyaml.util.UriEncoder.encode;

@Component
@PropertySource("classpath:application.properties")
public class InfectionInfoUtil {
    @Value("${open-api.key.public-data-api-key}")
    private String publicDataApiKey; // 애플리케이션 클라이언트 아이디값";

    public URL makeInfectionInfoApiUrl(String url, String pageNo, String numOfRows, Date startCreateDate, Date endCreateDate) throws IOException{
        String startDate = dateToString(startCreateDate, "yyyyMMdd");  // 날짜 형식 변환
        String endDate = dateToString(endCreateDate, "yyyyMMdd");  // 날짜 형식 변환

        StringBuilder urlBuilder = new StringBuilder(url);

        urlBuilder.append("?" + encode("ServiceKey") + "=" + publicDataApiKey); // Service Key
        urlBuilder.append("&" + encode("pageNo") + "=" + encode(pageNo)); // 페이지번호
        urlBuilder.append("&" + encode("numOfRows") + "=" + encode(numOfRows)); // 한 페이지 결과 수
        urlBuilder.append("&" + encode("startCreateDt") + "=" + encode(startDate)); // 검색할 생성일 범위의 시작
        urlBuilder.append("&" + encode("endCreateDt") + "=" + encode(endDate)); // 검색할 생성일 범위의 종료

        return new URL(urlBuilder.toString());
    }
}

